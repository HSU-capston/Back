package capstone.SportyUp.SportyUp_Server.service.BowlingService;

import capstone.SportyUp.SportyUp_Server.converter.BowlingConverter;
import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
@Slf4j
public class BowlingCommandServiceImpl implements BowlingCommandService {

    private final String FLASK_SERVER_URL = "http://127.0.0.1:5000";  // Flask 서버 URL (예시: http://localhost:5000)
    private static final String UPLOAD_DIR = "C:\\Users\\EliteBook\\Documents\\GitHub\\Back\\src\\main\\resources\\cam\\"; // 업로드된 파일 저장 폴더
    private static final String UPLOAD_RESULT_DIR = "C:\\Users\\EliteBook\\Documents\\GitHub\\Back\\src\\main\\resources\\cam_after_flask\\";

    private BowlingConverter bowlingConverter;

    @Override
    public BowlingResponseDTO.BowlingAnalyzeResponseDTO analyzeBowling(BowlingRequestDTO.BowlingAnalyzeRequestDTO request) {

        MultipartFile bowlingVideo = request.getFile();
        Integer score = 0;

        if (bowlingVideo.isEmpty()) {
            //Todo: 파일이 비어있음
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
            File processedFile = sendFileToFlask(destination);
            String fileUrl = getProcessedFileUrl(processedFile.getName());

            //Todo: 파일 저장 성공
            return BowlingConverter.toBowlingAnalyzeResponseDTO(fileUrl);

        } catch (IOException e) {
            e.printStackTrace();
            //Todo: 파일 저장 중 오류 발생
            return null;
        }
    }

    private String getProcessedFileUrl(String fileName) {
        return "http://localhost:8080/processed-files/" + fileName;
    }

    private File sendFileToFlask(File file) throws IOException {
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
            ResponseEntity<byte[]> response = restTemplate.exchange(uri, HttpMethod.POST, entity, byte[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Flask 서버에서 처리된 파일을 저장할 경로 지정
                File processedFile = new File(UPLOAD_RESULT_DIR + file.getName());
                // 응답으로 받은 바이트 배열을 처리된 파일로 저장
                Files.write(processedFile.toPath(), response.getBody());
                System.out.println("Flask 서버로 파일 전송 및 처리 성공");
                return processedFile;
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
