package capstone.SportyUp.SportyUp_Server.web.controller;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.service.BowlingService.BowlingCommandService;
import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Bowling", description = "볼링 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bowling")
public class BowlingController {

    private final BowlingCommandService bowlingCommandService;

    @PostMapping("/analyze")
    public ApiResponse<BowlingResponseDTO.BowlingAnalyzeResponseDTO> analyzeBowling(@ModelAttribute BowlingRequestDTO.BowlingAnalyzeRequestDTO request){


        return ApiResponse.onSuccess(bowlingCommandService.analyzeBowling(request));
    }
}
