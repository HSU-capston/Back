package capstone.SportyUp.SportyUp_Server.service.GameService;

import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameResponseDTO;

import java.time.LocalDate;

public interface GameQueryService {
    public GameResponseDTO.ChartDTO getChart(Long userId, Long sportsId);
    public GameResponseDTO.GameDateListDTO getGameDateListAllCategory(Long userId, Integer year, Integer month);
    public GameResponseDTO.GameDateListDTO getGameDateListOneCategory(Long userId, Integer year, Integer month, Long sportsId);
    public GameResponseDTO.GameInfoListDTO getGameListAllCategory(Long userId, LocalDate date);
    public GameResponseDTO.GameInfoListDTO getGameListOneCategory(Long userId, LocalDate date, Long sportsId);
    public GameResponseDTO.GameDetailDTO getGameDetail(Long gameId);
}
