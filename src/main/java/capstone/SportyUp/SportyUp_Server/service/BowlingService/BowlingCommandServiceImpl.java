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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

@Service
@RequiredArgsConstructor
@Slf4j
public class BowlingCommandServiceImpl implements BowlingCommandService {

    private final String FLASK_SERVER_URL = "http://127.0.0.1:5000";  // Flask 서버 URL
    private static final String UPLOAD_DIR = "C:\\Users\\EliteBook\\Documents\\GitHub\\Back\\src\\main\\resources\\cam\\"; // 업로드된 파일 저장 폴더
    private static final String UPLOAD_RESULT_DIR = "C:\\Users\\EliteBook\\Documents\\GitHub\\Back\\src\\main\\resources\\cam_after_flask\\";

    private final WebClient webClient;

    @Override
    public BowlingResponseDTO.BowlingAnalyzeResponseDTO analyzeBowling(BowlingRequestDTO.BowlingAnalyzeRequestDTO request) {
        MultipartFile bowlingVideo = request.getFile();

        if (bowlingVideo.isEmpty()) {
            log.error("업로드된 파일이 비어있습니다.");
            return null;
        }

        try {
            // 저장할 경로 설정
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new IOException("디렉토리 생성 실패");
                }
            }

            // 저장할 파일 객체 생성
            String uniqueFileName = System.currentTimeMillis() + "_" + bowlingVideo.getOriginalFilename();
            File destination = new File(UPLOAD_DIR + uniqueFileName);
            bowlingVideo.transferTo(destination);

            log.info("파일 저장 위치: {}", destination.getAbsolutePath());

            // Flask 서버로 파일 전송 및 처리된 파일 받기
            long startTime = System.nanoTime();  // 시작 시간 기록
            File processedFile = sendFileToFlask(destination);
            long endTime = System.nanoTime();  // 종료 시간 기록

            long processingTime = (endTime - startTime) / 1_000_000;  // 처리 시간 (밀리초 단위)
            log.info("Flask 서버에서 동영상 처리 시간: {} ms", processingTime);

            String fileUrl = getProcessedFileUrl(processedFile.getName());
            log.info("처리된 파일 URL: {}", fileUrl);

            return BowlingConverter.toBowlingAnalyzeResponseDTO(fileUrl); // 응답 반환

        } catch (IOException e) {
            log.error("파일 처리 중 오류 발생: {}", e.getMessage(), e);
            return null; // 파일 저장 중 오류 발생
        }
    }

    private String getProcessedFileUrl(String fileName) {
        return "http://localhost:8080/processed-files/" + fileName; // 처리된 파일 URL 반환
    }

    public File sendFileToFlask(File file) throws IOException {
        try {
            // Multipart로 파일 전송
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(file));

            // WebClient로 파일 전송
            URI uri = URI.create(FLASK_SERVER_URL + "/upload");
            Mono<ResponseEntity<byte[]>> responseMono = webClient.post()
                    .uri(uri)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(body)
                    .retrieve()
                    .toEntity(byte[].class);

            ResponseEntity<byte[]> response = responseMono.block(); // 동기식 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                // 처리된 파일을 받기 위해 스트림 처리
                File processedFile = new File(UPLOAD_RESULT_DIR + file.getName());

                // 파일 저장시 'StandardOpenOption' 사용
                Path processedFilePath = processedFile.toPath();
                Files.write(processedFilePath, response.getBody(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

                return processedFile;
            } else {
                throw new IOException("Flask 서버에서 처리된 파일을 받는 데 실패했습니다.");
            }
        } catch (Exception e) {
            throw new IOException("Flask 서버로 파일을 전송하는 데 실패했습니다.", e);
        }
    }
}
