package capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    public static class BowlingInfoDTO{
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
    }

}
