package capstone.SportyUp.SportyUp_Server.repository;

import capstone.SportyUp.SportyUp_Server.domain.RefreshToken;
import capstone.SportyUp.SportyUp_Server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(User user);
}
