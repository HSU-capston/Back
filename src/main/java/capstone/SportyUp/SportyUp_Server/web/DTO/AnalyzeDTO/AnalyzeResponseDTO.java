package capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class AnalyzeResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChartDTO{
        Integer gameCount;  //전체 게임 수
        Integer averageScore; //전체 평균 점수
        Integer highScore;  //최고 점수
        Integer lowScore;   //최저 점수
        List<DateScoreDTO> dateScores;  //날짜별 점수 리스트
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DateScoreDTO{
        LocalDate gameDate; //해당 날짜에
        Integer gameScore;  //평균 몇 점인지
    }

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
    public static class BowlingResultDTO{
        Long id;
    }

}
