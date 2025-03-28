package capstone.SportyUp.SportyUp_Server.service.GameService;

import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameResponseDTO;

public interface GameQueryService {
    public GameResponseDTO.ChartDTO getChart(Long userId, Long sportsId);
    public GameResponseDTO.GameDateListDTO getGameDateListAllCategory(Long userId, Integer year, Integer month);
}
