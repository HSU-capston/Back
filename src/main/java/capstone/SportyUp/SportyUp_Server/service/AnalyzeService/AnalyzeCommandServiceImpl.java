package capstone.SportyUp.SportyUp_Server.service.AnalyzeService;

import capstone.SportyUp.SportyUp_Server.apiPayload.Exception.GameHandler;
import capstone.SportyUp.SportyUp_Server.apiPayload.Exception.UserHandler;
import capstone.SportyUp.SportyUp_Server.apiPayload.code.status.ErrorStatus;
import capstone.SportyUp.SportyUp_Server.aws.s3.S3Uploader;
import capstone.SportyUp.SportyUp_Server.converter.AnalyzeConverter;
import capstone.SportyUp.SportyUp_Server.domain.*;
import capstone.SportyUp.SportyUp_Server.domain.enums.PoseScore;
import capstone.SportyUp.SportyUp_Server.repository.AnalyzeRepository;
import capstone.SportyUp.SportyUp_Server.repository.GameRepository;
import capstone.SportyUp.SportyUp_Server.repository.UserRepository;
import capstone.SportyUp.SportyUp_Server.repository.UserSportsRepository;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyzeCommandServiceImpl implements AnalyzeCommandService {

    private final GameRepository gameRepository;
    private final AnalyzeRepository analyzeRepository;
    private final UserRepository userRepository;
    private final UserSportsRepository userSportsRepository;

    @Value("${flask}")
    private String FLASK_SERVER_URL;  // Flask 서버 URL (예시: http://localhost:5000)
    private final S3Uploader s3Uploader;
//    @Value("${local-path.windows.upload}")
//    private String UPLOAD_DIR;
//    //    private static final String UPLOAD_DIR = "C:\\Users\\EliteBook\\Documents\\GitHub\\Back\\src\\main\\resources\\cam\\"; // 업로드된 파일 저장 폴더
//    @Value("${local-path.windows.result}")
//    private String UPLOAD_RESULT_DIR;

    @Override
    public AnalyzeResponseDTO.AnalyzeResultDTO requestAnalyze(Long userId, Long gameId, AnalyzeRequestDTO.BowlingDTO request) {

        User user = userRepository.findById(userId).orElseThrow(()->new UserHandler(ErrorStatus.USER_NOT_FOUND));
        //분석할 영상이 속한 게임 찾기
        Game game = gameRepository.findById(gameId).orElseThrow(()->new GameHandler(ErrorStatus.GAME_NOT_FOUND));
        Sports sports = game.getSports();
        UserSports userSports = userSportsRepository.findTop1ByUserAndSports(user,sports);
        //영상찾기
        MultipartFile uploadedFile = request.getFile();

        if (uploadedFile.isEmpty()) return null;

        //파일 저장
        //Todo: 저장하지 않고 직접 파일을 보내도록
//        File savedFile = saveFile(uploadedFile);

        //S3에 원본 영상 업로드
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String rawKey = String.format("original-videos/user_%d/game_%d/original_%s.mp4", userId, gameId, timestamp);
        String analyzedKey = String.format("analyzed-videos/user_%d/game_%d/analyzed_%s.mp4", userId, gameId, timestamp);
        String uploadedVideoUrl;
        try{
            uploadedVideoUrl = s3Uploader.upload(uploadedFile, rawKey);
        } catch (IOException e){
            throw new RuntimeException("S3 업로드 실패", e);
        }
        String userLevel = userSports.getLevel().toString();

        //Flask 요청
        Map<String, Object> flaskResponse = processFileWithFlask(sports,uploadedVideoUrl,analyzedKey,userLevel);

        //Flask 응답
        String videoUrl = (String) flaskResponse.get("video_url");
        String recommendPose = (String) flaskResponse.get("recommend");
        String goodPoint = (String) flaskResponse.get("good");
        String badPoint = (String) flaskResponse.get("bad");
        PoseScore poseScore = PoseScore.valueOf(flaskResponse.get("grade").toString());
        Integer score = (Integer)flaskResponse.get("score");
        Integer shoulder_angle_diff = (Integer) flaskResponse.get("shoulder_angle_diff");
        Integer movement_distance = (Integer) flaskResponse.get("movement_distance");
        Integer wrist_movement_total = (Integer) flaskResponse.get("wrist_movement_total");
        Integer ankle_switch_count = (Integer) flaskResponse.get("ankle_switch_count");

//          PoseScore poseScore = evaluateScore(score.intValue());

        //AnalyzeEntity 저장
        AnalyzeEntity newAnalyzeEntity = AnalyzeEntity.builder()
                .user(user)
                .game(game)
                .videoUrl(videoUrl)
                .score(score)
                .poseScore(poseScore)
                .goodPoint(goodPoint)
                .badPoint(badPoint)
                .recommendPose(recommendPose)
                .shoulderAngleDiff(shoulder_angle_diff)
                .movementDistance(movement_distance)
                .wristMovementTotal(wrist_movement_total)
                .ankleSwitchCount(ankle_switch_count)
                .build();

        newAnalyzeEntity = analyzeRepository.save(newAnalyzeEntity);

        return AnalyzeConverter.toAnalyzeResultDTO(newAnalyzeEntity);
    }


    private Map<String, Object> sendFileToFlask(String videoUrl, String analyzedKey, String userLevel) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // Flask 서버에 보낼 파일 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("video_url", videoUrl);
        body.add("user_level", userLevel);
        body.add("analyzed_key", analyzedKey);

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // HTTP 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            // Flask 서버의 /upload 엔드포인트로 파일 업로드 요청
            URI uri = URI.create(FLASK_SERVER_URL + "/upload");
            ResponseEntity<Map> response = restTemplate.exchange(uri, HttpMethod.POST, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Flask 서버에서 반환된 데이터를 받아옴
                return response.getBody();  // Flask 서버에서 반환된 JSON 응답을 받아옴
            } else {
                System.out.println("Flask 서버로 파일 전송 실패: " + response.getStatusCode());
                throw new IOException("Flask 서버에서 처리된 파일을 받는 데 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Flask 서버로 파일을 전송하는데 실패했습니다.", e);
        }
    }

//    private File saveFile(MultipartFile file) {
//        try {
//            File dir = new File(UPLOAD_DIR);
//            if (!dir.exists()) dir.mkdirs();
//
//            File destination = new File(UPLOAD_DIR + file.getOriginalFilename());
//            file.transferTo(destination);
//            return destination;
//        } catch (IOException e) {
//            throw new RuntimeException("파일 저장 실패: " + e.getMessage(), e);
//        }
//    }

    private Map<String, Object> processFileWithFlask(Sports sports, String videoUrl, String analyzedKey, String userLevel) {
        if ("볼링".equals(sports.getName())) {
            try {
                return sendFileToFlask(videoUrl, analyzedKey, userLevel);
            } catch (IOException e) {
                throw new RuntimeException("Flask 처리 실패", e);
            }
        }

        // 다른 종목은 추후 구현
        return Map.of(
                "video_url", "",
                "recommend", "",
                "good", "",
                "bad", "",
                "score", 0.0
        );
    }

//    private PoseScore evaluateScore(int score) {
//        if (score <= 33) return PoseScore.BAD;
//        if (score <= 66) return PoseScore.GOOD;
//        return PoseScore.EXCELLENT;
//    }
}
