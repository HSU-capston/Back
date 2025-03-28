package capstone.SportyUp.SportyUp_Server.web.controller.specification;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

public interface AnalyzeSpecification {
    @GetMapping("/{analyzeId}")
    @Operation(summary = "상세 분석 조회 API", description = "하나의 분석 내용을 조회하는 API입니다. PathVariable로 analyzeId 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    ApiResponse<AnalyzeResponseDTO.AnalyzeDetailDTO> getAnalyze(@PathVariable Long analyzeId);

    @GetMapping("/{gameId}/list")
    @Operation(summary = "상세 분석 리스트 조회 API", description = "한 게임의 분석들의 리스트를 조회하는 API입니다. PathVariable로 gameId 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    ApiResponse<AnalyzeResponseDTO.AnalyzeInfoListDTO> getAnalyzeList(@PathVariable Long gameId);

    @PostMapping(path = "/{gameId}", consumes = "multipart/form-data")
    @Operation(summary = "분석 요청 API", description = "영상 분석을 요청하는 API입니다. PathVariable로 gameId 필요, 요청할 동영상파일 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    ApiResponse<AnalyzeResponseDTO.AnalyzeResultDTO> requestAnalyze(@RequestParam Long userId,@PathVariable Long gameId, @ModelAttribute AnalyzeRequestDTO.BowlingDTO request);
}
