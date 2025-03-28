package capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GameResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameDateListDTO{
        List<LocalDate> gameDateList;  //게임이 있는 날짜 리스트
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameInfoDTO{
        Long id;        //게임 번호
        String sports;  //스포츠 종목
        LocalDate playDate; //게임 진행 날짜
        Integer score;  //점수
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameInfoListDTO{
        List<GameInfoDTO> gameInfoList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameDetailDTO{
        Long id;
        String playDate;
        String summary; //게임 요약
        Integer score;
        String highlightUrl;    //하이라이트 url
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResultDTO{
        Long id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EndResultDTO{
        Long id;
        String summary;
        String highlightUrl;
        Integer score;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChartDTO{
        Long gameCount;  //전체 게임 수
        Double averageScore; //전체 평균 점수
        Integer highScore;  //최고 점수
        Integer lowScore;   //최저 점수
        List<GameResponseDTO.DateScoreDTO> dateScores;  //날짜별 점수 리스트
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DateScoreDTO{
        LocalDateTime gameDate; //해당 날짜에
        Integer gameScore;  //몇 점인지
    }
}
