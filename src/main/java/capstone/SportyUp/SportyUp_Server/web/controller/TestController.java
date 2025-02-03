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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final String UPLOAD_DIR = "C:\\Users\\EliteBook\\Documents\\GitHub\\Back\\src\\main\\resources\\cam\\";

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

            // Flask 서버로 파일 전송
            sendFileToFlask(destination);

            return ResponseEntity.ok("파일 업로드 성공: " + destination.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("파일 저장 중 오류 발생");
        }
    }

    private void sendFileToFlask(File file) throws IOException {
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
            URI uri = UriComponentsBuilder.fromHttpUrl("http://127.0.0.1:5000/upload").build().toUri();
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Flask 서버로 파일 업로드 성공");
            } else {
                System.out.println("Flask 서버 업로드 실패: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error while sending file to Flask server", e);
        }
    }
}
