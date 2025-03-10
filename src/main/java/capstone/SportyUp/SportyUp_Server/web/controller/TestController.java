package capstone.SportyUp.SportyUp_Server.web.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final String UPLOAD_DIR = "C:\\Users\\EliteBook\\Documents\\GitHub\\Back\\src\\main\\resources\\cam\\";
    private static final String UPLOAD_RESULT_DIR = "C:\\Users\\EliteBook\\Documents\\GitHub\\Back\\src\\main\\resources\\cam_after_flask\\";
    private final String FLASK_SERVER_URL = "http://127.0.0.1:5000";  // Flask 서버 URL (예시: http://localhost:5000)

    @PostMapping("/message")
    public ResponseEntity<String> messageTest(@RequestParam String request){
        System.out.println(request);
        return ResponseEntity.ok(request);
    }

    @PostMapping("/cam")
    public ResponseEntity<String> camTest(@RequestParam("file") MultipartFile file){

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
        }

        try {
            // 저장할 경로 설정
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉터리 없으면 생성
            }

            // 저장할 파일 객체 생성
            File destination = new File(UPLOAD_DIR + file.getOriginalFilename());
            file.transferTo(destination);

            // Flask 서버로 파일 전송 및 처리된 파일 받기
            File processedFile = sendFileToFlask(destination);

            return ResponseEntity.ok("파일 업로드 성공: " + destination.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("파일 저장 중 오류 발생");
        }
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
