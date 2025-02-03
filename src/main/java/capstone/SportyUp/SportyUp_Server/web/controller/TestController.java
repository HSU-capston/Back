package capstone.SportyUp.SportyUp_Server.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {
    private static final String UPLOAD_DIR = "C:\\Users\\EliteBook\\Documents\\GitHub\\Back\\src\\main\\resources\\cam\\"; // 업로드된 파일 저장 폴더

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

            return ResponseEntity.ok("파일 업로드 성공: " + destination.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("파일 저장 중 오류 발생");
        }

    }
}
