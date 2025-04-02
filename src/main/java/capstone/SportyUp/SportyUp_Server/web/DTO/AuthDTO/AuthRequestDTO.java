package capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SmsSendDTO{
        @NotNull
        String phoneNum;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SmsVerifyDTO{
        @NotNull
        String phoneNum;

        @NotNull
        String code;
    }
}
