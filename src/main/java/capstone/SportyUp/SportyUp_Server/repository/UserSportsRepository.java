package capstone.SportyUp.SportyUp_Server.repository;

import capstone.SportyUp.SportyUp_Server.domain.User;
import capstone.SportyUp.SportyUp_Server.domain.UserSports;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSportsRepository extends JpaRepository<UserSports, Long> {
    Optional<UserSports> findByUser(User user);
}
