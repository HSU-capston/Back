package capstone.SportyUp.SportyUp_Server.web.controller;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.service.HomeService.HomeRecommandService;
import capstone.SportyUp.SportyUp_Server.web.DTO.HomeDTO.HomeResponseDTO;
import capstone.SportyUp.SportyUp_Server.web.controller.specification.HomeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController implements HomeSpecification {
    private final HomeRecommandService homeRecommandService;
    @Override
    public ApiResponse<HomeResponseDTO.RecommendedVideoListDTO> getRecommendedVideos() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        return ApiResponse.onSuccess(homeRecommandService.getRecommendedVideos(userId));
    }
}
