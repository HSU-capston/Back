package capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AnalyzeResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BowlingDTO{
        Integer poseScore;  //자세 점수
        String recommendPose;   //다음 추천 자세
        String videoUrl;    //분석영상 url
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnalyzeResultDTO{
        Long id;
        String poseScore;
        String recommendPose;
        String videoUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnalyzeInfoDTO{
        Long id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnalyzeInfoListDTO{
        Integer listSize;
        List<AnalyzeInfoDTO> analyzeList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnalyzeDetailDTO{
        Long id;
        String poseScore;
        String recommendPose;
        String videoUrl;
    }

}
