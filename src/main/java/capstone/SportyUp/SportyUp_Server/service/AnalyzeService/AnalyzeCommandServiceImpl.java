package capstone.SportyUp.SportyUp_Server.service.AnalyzeService;

import capstone.SportyUp.SportyUp_Server.converter.AnalyzeConverter;
import capstone.SportyUp.SportyUp_Server.domain.AnalyzeEntity;
import capstone.SportyUp.SportyUp_Server.domain.Game;
import capstone.SportyUp.SportyUp_Server.domain.Sports;
import capstone.SportyUp.SportyUp_Server.domain.User;
import capstone.SportyUp.SportyUp_Server.domain.enums.PoseScore;
import capstone.SportyUp.SportyUp_Server.repository.AnalyzeRepository;
import capstone.SportyUp.SportyUp_Server.repository.GameRepository;
import capstone.SportyUp.SportyUp_Server.repository.UserRepository;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyzeCommandServiceImpl implements AnalyzeCommandService {

    private final GameRepository gameRepository;
    private final AnalyzeRepository analyzeRepository;
    private final UserRepository userRepository;
    private final String FLASK_SERVER_URL = "http://localhost:5000";  // Flask 서버 URL (예시: http://localhost:5000)
    @Value("${local-path.windows.upload}")
    private String UPLOAD_DIR;
    //    private static final String UPLOAD_DIR = "C:\\Users\\EliteBook\\Documents\\GitHub\\Back\\src\\main\\resources\\cam\\"; // 업로드된 파일 저장 폴더
    @Value("${local-path.windows.result}")
    private String UPLOAD_RESULT_DIR;

    @Override
    public AnalyzeResponseDTO.AnalyzeResultDTO requestAnalyze(Long userId, Long gameId, AnalyzeRequestDTO.BowlingDTO request) {

        User user = userRepository.findById(userId).orElse(null);
        //분석할 영상이 속한 게임 찾기
        Game targetGame = gameRepository.findById(gameId).orElse(null);
        Sports targetSports = targetGame.getSports();

        //영상찾기
        MultipartFile targetVideo = request.getFile();
        String fileUrl = "";
        String videoUrl = "";
        String message1 = "";
        String message2 = "";
        Double scoreDouble = (double) 0;
        Integer score = 0;  // Double을 Integer로 변환
        PoseScore poseScore = PoseScore.EXCELLENT;
        if (targetVideo.isEmpty()) {
            return null;
        }

        try {
            // 저장할 경로 설정
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉터리 없으면 생성
            }

            // 저장할 파일 객체 생성
            File destination = new File(UPLOAD_DIR + targetVideo.getOriginalFilename());
            System.out.println("파일 이름: " + targetVideo.getOriginalFilename());
            targetVideo.transferTo(destination);


            System.out.println(targetSports.getName());
            // Flask 서버로 파일 전송 및 처리된 파일 받기
            switch(targetSports.getName()){
                case "볼링":
                    System.out.println("볼링분석스");
                    // Flask 서버로 파일 전송 및 처리된 파일 받기
                    Map<String, Object> response = sendFileToFlask(destination);
                    videoUrl = (String) response.get("video_url");
                    message1 = (String) response.get("message1");
                    message2 = (String) response.get("message2");
                    scoreDouble = (Double) response.get("score");
                    score = scoreDouble.intValue();  // Double을 Integer로 변환
                    break;
                case "당구":
                    break;
                case "골프":
                    break;
                case "야구":
                    break;
            }

            System.out.println("Processed Url : " + fileUrl);


        } catch (IOException e) {
            System.out.println("파일 저장 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
        }

        if(score>=0 && score<=33){
            poseScore = PoseScore.BAD;
        }else if(score>33 && score<=66){
            poseScore = PoseScore.GOOD;
        }else{
            poseScore = PoseScore.EXCELLENT;
        }

        AnalyzeEntity newAnalyzeEntity = AnalyzeEntity.builder()
                .user(user)
                .game(targetGame)
                .videoUrl(videoUrl)
                .poseScore(poseScore)
                .recommendPose(message1)
                .build();

        newAnalyzeEntity = analyzeRepository.save(newAnalyzeEntity);

        return AnalyzeConverter.toAnalyzeResultDTO(newAnalyzeEntity);
    }

    private String getProcessedFileUrl(String fileName) {
        return "http://localhost:8080/processed-files/" + fileName;
    }

    private Map<String, Object> sendFileToFlask(File file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // Flask 서버에 보낼 파일 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

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
}
