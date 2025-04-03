package capstone.SportyUp.SportyUp_Server.repository;

import capstone.SportyUp.SportyUp_Server.domain.SmsVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsVerificationRepository extends JpaRepository<SmsVerification, Long> {
    //전화번호, 유효기간이 지금 이후, 인증상태가 false인 엔티티 찾기
    Optional<SmsVerification> findByPhoneNumAndExpiresAtAfterAndVerifiedIsFalse(String phoneNum, LocalDateTime currentDateTime);
    Optional<SmsVerification> findTop1ByPhoneNumAndVerifiedIsTrueAndExpiresAtAfterOrderByCreatedAtDesc(String phoneNum, LocalDateTime currentDateTime);

}
