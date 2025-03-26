package capstone.SportyUp.SportyUp_Server.service.AnalyzeService;

import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeResponseDTO;

public interface AnalyzeCommandService {
    public AnalyzeResponseDTO.AnalyzeResultDTO requestAnalyze(Long userId, Long gameId, AnalyzeRequestDTO.BowlingDTO request);
}
