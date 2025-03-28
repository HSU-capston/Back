package capstone.SportyUp.SportyUp_Server.web.controller;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.service.AnalyzeService.AnalyzeCommandService;
import capstone.SportyUp.SportyUp_Server.service.AnalyzeService.AnalyzeQueryService;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeResponseDTO;
import capstone.SportyUp.SportyUp_Server.web.controller.specification.AnalyzeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analyzes")
public class AnalyzeController implements AnalyzeSpecification {
    private final AnalyzeCommandService analyzeCommandService;
    private final AnalyzeQueryService analyzeQueryService;

    @Override
    public ApiResponse<AnalyzeResponseDTO.BowlingDTO> getAnalyze(Long analyzeId) {
        return null;
    }

    @Override
    public ApiResponse<AnalyzeResponseDTO.AnalyzeInfoListDTO> getAnalyzeList(Long gameId) {

        return ApiResponse.onSuccess(analyzeQueryService.getAnalyzeList(gameId));
    }

    @Override
    public ApiResponse<AnalyzeResponseDTO.AnalyzeResultDTO> requestAnalyze(Long userId, Long gameId, AnalyzeRequestDTO.BowlingDTO request) {



        return ApiResponse.onSuccess(analyzeCommandService.requestAnalyze(userId, gameId, request));
    }
}
