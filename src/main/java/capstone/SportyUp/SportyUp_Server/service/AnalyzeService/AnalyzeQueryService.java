package capstone.SportyUp.SportyUp_Server.service.AnalyzeService;

import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeResponseDTO;

public interface AnalyzeQueryService {
    public AnalyzeResponseDTO.AnalyzeInfoListDTO getAnalyzeList(Long gameId);
}
