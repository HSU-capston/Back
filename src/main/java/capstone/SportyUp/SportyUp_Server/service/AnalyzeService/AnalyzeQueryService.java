package capstone.SportyUp.SportyUp_Server.service.AnalyzeService;

import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeResponseDTO;

public interface AnalyzeQueryService {
    public AnalyzeResponseDTO.ChartDTO getChart(Long userId, Long sportsId);
}
