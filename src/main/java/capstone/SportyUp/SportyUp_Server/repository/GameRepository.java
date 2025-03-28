package capstone.SportyUp.SportyUp_Server.repository;

import capstone.SportyUp.SportyUp_Server.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByUserIdAndSportsId(Long userId, Long sportsId);

    List<Game> findTop6ByUserIdAndSportsIdOrderByPlayDateDesc(Long userId, Long sportsId);  //최신순 6개게임
}

