package capstone.SportyUp.SportyUp_Server.service.SportsService;

import capstone.SportyUp.SportyUp_Server.converter.SportsConverter;
import capstone.SportyUp.SportyUp_Server.domain.Sports;
import capstone.SportyUp.SportyUp_Server.repository.SportsRepository;
import capstone.SportyUp.SportyUp_Server.web.DTO.SportsDTO.SportsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportsQueryServiceImpl implements SportsQueryService {
    private final SportsRepository sportsRepository;
    @Override
    public SportsResponseDTO.SportsListDTO getSportsList() {
        List<Sports> sportsList = sportsRepository.findAll();

        return SportsConverter.toSportsListDTO(sportsList);
    }
}
