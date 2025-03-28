package capstone.SportyUp.SportyUp_Server.converter;

import capstone.SportyUp.SportyUp_Server.domain.AnalyzeEntity;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeConverter{
    public static AnalyzeResponseDTO.AnalyzeResultDTO toAnalyzeResultDTO(AnalyzeEntity analyzeEntity){
           return AnalyzeResponseDTO.AnalyzeResultDTO.builder()
                   .id(analyzeEntity.getId())
                   .poseScore(String.valueOf(analyzeEntity.getPoseScore()))
                   .recommendPose(analyzeEntity.getRecommendPose())
                   .videoUrl(analyzeEntity.getVideoUrl())
                   .build();
    }

    public static AnalyzeResponseDTO.AnalyzeInfoListDTO toAnalyzeInfoListDTO(List<AnalyzeEntity> analyzeList){
        List<AnalyzeResponseDTO.AnalyzeInfoDTO> analyzeInfoDTOList = new ArrayList<>();

        for(AnalyzeEntity analyzeEntity : analyzeList){
            analyzeInfoDTOList.add(AnalyzeResponseDTO.AnalyzeInfoDTO.builder()
                            .id(analyzeEntity.getId())
                            .build());
        }

        return AnalyzeResponseDTO.AnalyzeInfoListDTO.builder()
                .analyzeList(analyzeInfoDTOList)
                .listSize(analyzeInfoDTOList.size())
                .build();
    }
}
