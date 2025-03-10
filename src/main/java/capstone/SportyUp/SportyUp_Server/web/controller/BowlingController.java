package capstone.SportyUp.SportyUp_Server.web.controller;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.service.BowlingService.BowlingCommandService;
import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bowling")
public class BowlingController {

    private final BowlingCommandService bowlingCommandService;

    private static final String UPLOAD_RESULT_DIR = "C:\\Users\\EliteBook\\Documents\\GitHub\\Back\\src\\main\\resources\\cam_after_flask\\"; // 비디오 파일 저장 경로

    @PostMapping("/analyze")
    public ApiResponse<BowlingResponseDTO.BowlingAnalyzeResponseDTO> analyzeBowling(@ModelAttribute BowlingRequestDTO.BowlingAnalyzeRequestDTO request) {
        return ApiResponse.onSuccess(bowlingCommandService.analyzeBowling(request));
    }

    // 처리된 비디오 파일을 반환하는 GET 엔드포인트 추가
    @GetMapping("/processed-files/{fileName}")
    public ResponseEntity<FileSystemResource> getProcessedVideo(@PathVariable String fileName) {
        File videoFile = new File(UPLOAD_RESULT_DIR + fileName);

        if (!videoFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 비디오 파일 반환 시 Content-Type을 video/mp4로 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("video/mp4"));
        headers.setContentLength(videoFile.length());

        FileSystemResource fileResource = new FileSystemResource(videoFile);
        return new ResponseEntity<>(fileResource, headers, HttpStatus.OK);
    }
}
