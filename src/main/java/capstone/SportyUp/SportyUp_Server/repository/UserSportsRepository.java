package capstone.SportyUp.SportyUp_Server.repository;

import capstone.SportyUp.SportyUp_Server.domain.UserSports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSportsRepository extends JpaRepository<UserSports, Long> {
}
