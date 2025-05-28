package capstone.SportyUp.SportyUp_Server.web.DTO.HomeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class HomeResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendedVideoDTO {
        String videoUrl;
        String thumbnailUrl;
        String title;
        String channelTitle;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendedVideoListDTO {
        Integer listSize;
        List<RecommendedVideoDTO> recommendedVideoList;
    }
}
