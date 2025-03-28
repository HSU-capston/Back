package capstone.SportyUp.SportyUp_Server.repository;

import capstone.SportyUp.SportyUp_Server.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByUserIdAndSportsId(Long userId, Long sportsId);

    List<Game> findTop6ByUserIdAndSportsIdOrderByPlayDateDesc(Long userId, Long sportsId);  //최신순 6개게임

    @Query("SELECT g FROM Game g WHERE g.user.id = :userId AND g.sports.id = :sportsId AND YEAR(g.playDate) = :year AND MONTH(g.playDate) = :month ORDER BY g.playDate DESC")
    List<Game> findByUserIdAndSportsIdAndYearAndMonth(
            @Param("userId") Long userId,
            @Param("sportsId") Long sportsId,
            @Param("year") Integer year,
            @Param("month") Integer month
    );

    @Query("SELECT g FROM Game g WHERE g.user.id = :userId AND YEAR(g.playDate) = :year AND MONTH(g.playDate) = :month ORDER BY g.playDate DESC")
    List<Game> findByUserIdAndYearAndMonth(
            @Param("userId") Long userId,
            @Param("year") Integer year,
            @Param("month") Integer month
    );
}

