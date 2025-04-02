package capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SmsSendResultDTO{
        private String phoneNum;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SmsVerifyResultDTO{
        private String phoneNum;
        private String code;
    }
}
