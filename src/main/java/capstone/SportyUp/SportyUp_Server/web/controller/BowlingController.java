package capstone.SportyUp.SportyUp_Server.web.controller;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingResponseDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Bowling", description = "볼링 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bowling")
public class BowlingController {

    @PostMapping("/analyze")
    public ApiResponse<BowlingResponseDTO.BowlingAnalyzeResponseDTO> analyzeBowling(@RequestBody BowlingRequestDTO.BowlingAnalyzeRequestDTO request){

        return null;
    }
}
