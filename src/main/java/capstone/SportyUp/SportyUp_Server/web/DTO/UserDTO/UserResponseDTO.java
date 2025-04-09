package capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDTO{
        @NotBlank
        private String name;
        @NotBlank
        private String email;
        @NotBlank
        private String password;
        @NotBlank
        private String phoneNum;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpResultDTO{
        @NotBlank
        private String accessToken;
        @NotBlank
        private String refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO{
        @NotBlank
        private String accessToken;
        @NotBlank
        private String refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SurveyResultDTO{
        @NotBlank
        private String message;
    }

}
