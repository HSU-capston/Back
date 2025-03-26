package capstone.SportyUp.SportyUp_Server.converter;

import capstone.SportyUp.SportyUp_Server.domain.AnalyzeEntity;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeResponseDTO;

public class AnalyzeConverter{
    public static AnalyzeResponseDTO.AnalyzeResultDTO toAnalyzeResultDTO(AnalyzeEntity analyzeEntity){
           return AnalyzeResponseDTO.AnalyzeResultDTO.builder()
                   .id(analyzeEntity.getId())
                   .poseScore(String.valueOf(analyzeEntity.getPoseScore()))
                   .recommendPose(analyzeEntity.getRecommendPose())
                   .videoUrl(analyzeEntity.getVideoUrl())
                   .build();
    }
}
