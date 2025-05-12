package capstone.SportyUp.SportyUp_Server.repository;

import capstone.SportyUp.SportyUp_Server.domain.AnalyzeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnalyzeRepository extends JpaRepository<AnalyzeEntity, Long> {
    List<AnalyzeEntity> findByGameId(Long gameId);
    Optional<AnalyzeEntity> findTopByGameIdOrderByScoreDesc(Long gameId);
}
