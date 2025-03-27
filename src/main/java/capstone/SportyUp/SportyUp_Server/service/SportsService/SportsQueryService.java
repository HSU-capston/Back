package capstone.SportyUp.SportyUp_Server.service.SportsService;

import capstone.SportyUp.SportyUp_Server.web.DTO.SportsDTO.SportsResponseDTO;

public interface SportsQueryService {
    public SportsResponseDTO.SportsListDTO getSportsList();
}
