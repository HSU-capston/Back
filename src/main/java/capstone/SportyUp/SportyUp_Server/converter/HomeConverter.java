package capstone.SportyUp.SportyUp_Server.converter;

import capstone.SportyUp.SportyUp_Server.web.DTO.HomeDTO.HomeResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class HomeConverter {
    public static HomeResponseDTO.RecommendedVideoListDTO toRecommendedVideoListDTO(List<String> videoUrls, List<String> thumbnailUrls){
        List<HomeResponseDTO.RecommendedVideoDTO> recommendedVideoDTOList = new ArrayList<>();

        int size = Math.min(videoUrls.size(), thumbnailUrls.size());
        for (int i = 0; i < size; i++) {
            String videoUrl = videoUrls.get(i);
            String thumbnailUrl = thumbnailUrls.get(i);
            recommendedVideoDTOList.add(toRecommendedVideoDTO(videoUrl, thumbnailUrl));
        }

        return HomeResponseDTO.RecommendedVideoListDTO.builder()
                .listSize(size)
                .recommendedVideoList(recommendedVideoDTOList)
                .build();
    }

    public static HomeResponseDTO.RecommendedVideoDTO toRecommendedVideoDTO(String videoUrl, String thumbnailUrl){
        return HomeResponseDTO.RecommendedVideoDTO.builder()
                .videoUrl(videoUrl)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
