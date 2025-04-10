package capstone.SportyUp.SportyUp_Server.service.HomeService;

import capstone.SportyUp.SportyUp_Server.web.DTO.HomeDTO.HomeResponseDTO;

public interface HomeRecommandService {
    public HomeResponseDTO.RecommendedVideoListDTO getRecommendedVideos(Long userId);
}
