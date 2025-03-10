package capstone.SportyUp.SportyUp_Server.service.BowlingService;

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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class BowlingCommandServiceImpl implements BowlingCommandService {

    private final String FLASK_SERVER_URL = "http://127.0.0.1:5000";  // Flask 서버 URL (예시: http://localhost:5000)
    private static final String UPLOAD_DIR = "D:\\25-1\\CapstoneDesign\\project\\SportyUp-Server\\src\\main\\resources\\cam\\"; // 업로드된 파일 저장 폴더

    @Override
    public BowlingResponseDTO.BowlingAnalyzeResponseDTO analyzeBowling(BowlingRequestDTO.BowlingAnalyzeRequestDTO request) {

        MultipartFile bowlingVideo = request.getFile();
        Integer score = 0;

        //받은 동영상 저장
        if (bowlingVideo.isEmpty()) {
            //Todo: 오류처리
//            return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
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

            //업로드 성공 시 Flask에 분석 요청 보내기
            try {
                File analyzedVideo = sendFileToFlask(destination);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (IOException e) {
            e.printStackTrace();
            //Todo: 오류처리
//            return ResponseEntity.status(500).body("파일 저장 중 오류 발생");
        }


        return null;
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
            URI uri = UriComponentsBuilder.fromHttpUrl(FLASK_SERVER_URL+"/upload").build().toUri();
            ResponseEntity<File> response = restTemplate.exchange(uri, HttpMethod.POST, entity, File.class);

            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Flask 서버로 파일 업로드 성공");
            } else {
                System.out.println("Flask 서버 업로드 실패: " + response.getStatusCode());
            }

            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error while sending file to Flask server", e);
        }

    }

}
