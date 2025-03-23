package capstone.SportyUp.SportyUp_Server.web.controller.specification;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface AnalyzeSpecification {
    @GetMapping("/chart")
    @Operation(summary = "분석 페이지 차트 조회 API", description = "차트에 필요한 데이터를 조회하는 API입니다. 어떤 종목에 대한 차트인지 구분 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    ApiResponse<AnalyzeResponseDTO.ChartDTO> getChart();

    @GetMapping("/bowling/{analyzeId}")
    @Operation(summary = "볼링 상세 분석 조회 API", description = "볼링 게임에 대한 분석을 조회하는 API입니다. PathVariable로 analyzeId 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    ApiResponse<AnalyzeResponseDTO.BowlingDTO> getBowlingAnalyze(@PathVariable Long analyzeId);

    @PostMapping("/{gameId}")
    @Operation(summary = "분석 요청 API", description = "볼링 영상 분석을 요청하는 API입니다. PathVariable로 gameId 필요, 요청할 동영상파일 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    ApiResponse<AnalyzeResponseDTO.BowlingResultDTO> requestAnalyzeBowling(@PathVariable Long gameId, @ModelAttribute AnalyzeRequestDTO.BowlingDTO request);
}
