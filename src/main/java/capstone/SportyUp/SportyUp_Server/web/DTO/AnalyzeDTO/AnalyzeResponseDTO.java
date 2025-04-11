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
    public static class AnalyzeResultDTO{   //실시간 분석에서는 다음 자세 추천만 보여줌
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
    public static class AnalyzeDetailDTO{   //분석내용 자세히 보기는 아쉬운점, 잘한점까지 보여줌
        Long id;
        String poseScore;
        String recommendPose;
        String goodPoint;
        String badPoint;
        String videoUrl;
    }

}
