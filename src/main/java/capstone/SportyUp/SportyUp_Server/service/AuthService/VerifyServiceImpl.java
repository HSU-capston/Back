package capstone.SportyUp.SportyUp_Server.service.AuthService;

import capstone.SportyUp.SportyUp_Server.apiPayload.Exception.AuthHandler;
import capstone.SportyUp.SportyUp_Server.apiPayload.code.status.ErrorStatus;
import capstone.SportyUp.SportyUp_Server.converter.AuthConverter;
import capstone.SportyUp.SportyUp_Server.domain.SmsVerification;
import capstone.SportyUp.SportyUp_Server.repository.SmsVerificationRepository;
import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {

    private final SmsVerificationRepository smsVerificationRepository;

    @Override
    @Transactional
    public AuthResponseDTO.SmsVerifyResultDTO verifySms(AuthRequestDTO.SmsVerifyDTO request) {
        SmsVerification smsVerification = smsVerificationRepository
                .findByPhoneNumAndExpiresAtAfterAndVerifiedIsFalse(request.getPhoneNum(), LocalDateTime.now())
                .orElseThrow(() -> new AuthHandler(ErrorStatus.AUTH_CODE_NOT_FOUND));

        if(smsVerification.getVerificationCode().equals(request.getCode())){    //사용자 입력과 코드가 일치하는 경우
            smsVerification.setVerified(true);

            return AuthConverter.toSmsVerifyResultDTO(smsVerification);
        }else{
            throw new AuthHandler(ErrorStatus.AUTH_CODE_MISMATCH);
        }

    }
}
