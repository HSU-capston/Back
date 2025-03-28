package capstone.SportyUp.SportyUp_Server.service.AnalyzeService;

import capstone.SportyUp.SportyUp_Server.converter.AnalyzeConverter;
import capstone.SportyUp.SportyUp_Server.domain.AnalyzeEntity;
import capstone.SportyUp.SportyUp_Server.repository.AnalyzeRepository;
import capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO.AnalyzeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyzeQueryServiceImpl implements AnalyzeQueryService {

    private final AnalyzeRepository analyzeRepository;

    @Override
    public AnalyzeResponseDTO.AnalyzeInfoListDTO getAnalyzeList(Long gameId) {

        List<AnalyzeEntity> analyzeList = analyzeRepository.findByGameId(gameId);

        return AnalyzeConverter.toAnalyzeInfoListDTO(analyzeList);
    }

    @Override
    public AnalyzeResponseDTO.AnalyzeDetailDTO getAnalyze(Long analyzeId) {

        AnalyzeEntity analyze = analyzeRepository.findById(analyzeId).orElse(null);

        return AnalyzeConverter.toAnalyzeDetailDTO(analyze);
    }
}
