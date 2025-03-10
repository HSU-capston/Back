package capstone.SportyUp.SportyUp_Server.service.BowlingService;

import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingResponseDTO;

public interface BowlingCommandService {

    public BowlingResponseDTO.BowlingAnalyzeResponseDTO analyzeBowling(BowlingRequestDTO.BowlingAnalyzeRequestDTO request);
}
