package capstone.SportyUp.SportyUp_Server.web.controller.specification;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.web.DTO.HomeDTO.HomeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;

public interface HomeSpecification {
    @GetMapping("/recommendations")
    @Operation(summary = "홈 화면 영상 추천 API", description = "홈 화면에서 추천영상을 가져오는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    ApiResponse<HomeResponseDTO.RecommendedVideoListDTO> getRecommendedVideos();

}
