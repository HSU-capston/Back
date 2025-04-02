package capstone.SportyUp.SportyUp_Server.converter;

import capstone.SportyUp.SportyUp_Server.domain.SmsVerification;
import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthResponseDTO;

public class AuthConverter {
    public static AuthResponseDTO.SmsSendResultDTO toSmsSendResultDTO(SmsVerification smsVerification){
        return AuthResponseDTO.SmsSendResultDTO.builder()
                .phoneNum(smsVerification.getPhoneNum())
                .build();
    }

    public static AuthResponseDTO.SmsVerifyResultDTO toSmsVerifyResultDTO(SmsVerification smsVerification){
        return AuthResponseDTO.SmsVerifyResultDTO.builder()
                .code(smsVerification.getVerificationCode())
                .build();
    }
}
