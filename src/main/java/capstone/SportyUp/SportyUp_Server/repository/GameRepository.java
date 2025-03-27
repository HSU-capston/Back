package capstone.SportyUp.SportyUp_Server.repository;

import capstone.SportyUp.SportyUp_Server.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    Long countByUserIdAndSportsId(Long userId, Long sportsId);

    @Query("SELECT AVG(g.score) FROM Game g WHERE g.user.id = :userId AND g.sports.id = :sportsId")
    Double findAverageScoreByUserIdAndSportsId(@Param("userId") Long userId, @Param("sportsId") Long sportsId);

    List<Game> findByUserIdAndSportsId(Long userId, Long sportsId);
}

