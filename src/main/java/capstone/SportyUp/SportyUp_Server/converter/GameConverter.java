package capstone.SportyUp.SportyUp_Server.converter;

import capstone.SportyUp.SportyUp_Server.domain.Game;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameResponseDTO;

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

    public static GameResponseDTO.ChartDTO toChartDTO(Long gameCount, Double averageScore, Integer highScore, Integer lowScore){
        return GameResponseDTO.ChartDTO.builder()
                .gameCount(gameCount)
                .averageScore(averageScore)
                .highScore(highScore)
                .lowScore(lowScore)
                .build();
    }
}
