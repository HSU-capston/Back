package capstone.SportyUp.SportyUp_Server.service.GameService;

import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameResponseDTO;

public interface GameCommandService {

    public GameResponseDTO.CreateResultDTO createGame(Long userId, GameRequestDTO.CreateDTO request);
    public GameResponseDTO.EndResultDTO endGame(Long gameId, GameRequestDTO.EndDTO request);
}
