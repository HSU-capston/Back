package capstone.SportyUp.SportyUp_Server.service.BowlingService;

import capstone.SportyUp.SportyUp_Server.converter.BowlingConverter;
import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BowlingCommandServiceImpl implements BowlingCommandService {

    private final String FLASK_SERVER_URL = "http://127.0.0.1:5000";  // Flask 서버 URL
    @Value("${local-path.windows.upload}")
    private String UPLOAD_DIR;

    @Value("${local-path.windows.result}")
    private String UPLOAD_RESULT_DIR;

    private BowlingConverter bowlingConverter;

    @Override
    public BowlingResponseDTO.BowlingAnalyzeResponseDTO analyzeBowling(BowlingRequestDTO.BowlingAnalyzeRequestDTO request) {

        MultipartFile bowlingVideo = request.getFile();

        if (bowlingVideo.isEmpty()) {
            // 파일이 비어있음
            return null;
        }

        try {
            // 저장할 경로 설정
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉터리 없으면 생성
            }

            // 저장할 파일 객체 생성
            File destination = new File(UPLOAD_DIR + bowlingVideo.getOriginalFilename());
            bowlingVideo.transferTo(destination);

            // Flask 서버로 파일 전송 및 처리된 파일 받기
            Map<String, Object> response = sendFileToFlask(destination);

            // Flask 서버에서 반환된 데이터를 출력
            String videoUrl = (String) response.get("video_url");
            String message1 = (String) response.get("message1");
            String message2 = (String) response.get("message2");
            Double scoreDouble = (Double) response.get("score");
            Integer score = scoreDouble.intValue();  // Double을 Integer로 변환

            // 출력
            System.out.println("Processed Video URL: " + saveProcessedVideo(videoUrl));
            System.out.println("Message 1: " + message1);
            System.out.println("Message 2: " + message2);
            System.out.println("Score: " + score);


            // 여기에 필요한 반환값을 응답 DTO로 반환
            return BowlingConverter.toBowlingAnalyzeResponseDTO(videoUrl);

        } catch (IOException e) {
            e.printStackTrace();
            // 파일 저장 중 오류 발생
            return null;
        }
    }

    // 비디오 파일을 Flask 서버에서 다운로드하여 로컬 디렉토리에 저장하는 메소드
    private String saveProcessedVideo(String videoUrl) throws IOException {
        // URL로부터 비디오 파일을 다운로드
        URL url = new URL(videoUrl);
        InputStream inputStream = url.openStream();

        // URL에서 파일 이름 추출 (cam_after.mp4와 같은 형태)
        String fileName = videoUrl.substring(videoUrl.lastIndexOf('/') + 1);

        // 원래 파일 이름을 사용하여 저장 경로 설정
        Path outputPath = Path.of(UPLOAD_RESULT_DIR, fileName);

        // 파일 다운로드 후 로컬에 저장
        Files.copy(inputStream, outputPath, StandardCopyOption.REPLACE_EXISTING);
        inputStream.close();

        return getProcessedFileUrl(fileName);
    }

    private String getProcessedFileUrl(String fileName) {
        return "http://113.198.83.187:8080/processed-files/" + fileName;
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
