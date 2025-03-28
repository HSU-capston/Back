package capstone.SportyUp.SportyUp_Server.converter;

import capstone.SportyUp.SportyUp_Server.domain.Game;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameResponseDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameConverter {
    public static GameResponseDTO.CreateResultDTO toCreateResultDTO(Long gameId){
        return GameResponseDTO.CreateResultDTO.builder()
                .id(gameId)
                .build();
    }

    public static GameResponseDTO.EndResultDTO toEndResultDTO(Game game){
        return GameResponseDTO.EndResultDTO.builder()
                .id(game.getId())
                .score(game.getScore())
                .summary(game.getSummary())
                .highlightUrl(game.getHighlightUrl())
                .build();
    }

    public static GameResponseDTO.ChartDTO toChartDTO(Long gameCount, Double averageScore, Integer highScore, Integer lowScore, List<Game> currentGameList){
        return GameResponseDTO.ChartDTO.builder()
                .gameCount(gameCount)
                .averageScore(averageScore)
                .highScore(highScore)
                .lowScore(lowScore)
                .dateScores(toDateScoreDTOList(currentGameList))
                .build();
    }

    public static List<GameResponseDTO.DateScoreDTO> toDateScoreDTOList(List<Game> gameList){
        List<GameResponseDTO.DateScoreDTO> result = new ArrayList<>();
        for(Game game : gameList){
            GameResponseDTO.DateScoreDTO dto = GameResponseDTO.DateScoreDTO.builder()
                    .gameDate(game.getPlayDate())
                    .gameScore(game.getScore())
                    .build();
            result.add(dto);
        }
        return result;
    }

    public static GameResponseDTO.GameDateListDTO toGameDateListDTO(List<Game> gameList){
        List<LocalDate> dateList = new ArrayList<>();
        for(Game game : gameList){
            dateList.add(game.getPlayDate().toLocalDate());
        }

        return GameResponseDTO.GameDateListDTO.builder()
                .gameDateList(dateList)
                .build();
    }

    public static GameResponseDTO.GameInfoListDTO toGameInfoListDTO(List<Game> gameList){
        List<GameResponseDTO.GameInfoDTO> result = new ArrayList<>();
        for(Game game : gameList){
            result.add(GameResponseDTO.GameInfoDTO.builder()
                    .id(game.getId())
                    .sportsId(game.getSports().getId())
                    .score(game.getScore())
                    .playDate(game.getPlayDate())
                    .build());
        }

        return GameResponseDTO.GameInfoListDTO.builder()
                .gameInfoList(result)
                .build();

    }

    public static GameResponseDTO.GameDetailDTO toGameDetailDTO(Game game){

        return GameResponseDTO.GameDetailDTO.builder()
                .id(game.getId())
                .summary(game.getSummary())
                .score(game.getScore())
                .playDate(game.getPlayDate())
                .highlightUrl(game.getHighlightUrl())
                .build();
    }
}
