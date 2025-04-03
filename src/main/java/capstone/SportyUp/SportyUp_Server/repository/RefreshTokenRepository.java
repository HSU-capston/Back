package capstone.SportyUp.SportyUp_Server.repository;

import capstone.SportyUp.SportyUp_Server.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
