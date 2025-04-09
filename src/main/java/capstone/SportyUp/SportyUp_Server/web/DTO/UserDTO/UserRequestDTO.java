package capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO;

import capstone.SportyUp.SportyUp_Server.domain.enums.UserSportsGoal;
import capstone.SportyUp.SportyUp_Server.domain.enums.UserSportsLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

public class UserRequestDTO {

    @Getter
    @Setter
    public static class SingUpDTO{
        @NotBlank
        private String email;
        @NotBlank
        private String password;
        @NotBlank
        private String nickname;
        @NotBlank
        private String phoneNum;
        @NotBlank
        private LocalDate birthday;
    }




    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateUserInfoDTO {
        @NotBlank(message = "필수 입력 항목입니다.")
        @Size(min = 1, max = 20, message = "크기는 1에서 20 사이입니다.")
        private String name;

        @NotBlank(message = "필수 입력 항목입니다.")
        @Email(message = "이메일 형식에 맞춰주세요.")
        private String email;

        @NotBlank(message = "필수 입력 항목입니다.")
        @Size(min = 1, max = 50, message = "크기는 1에서 50 사이입니다.")
        private String password;

        @NotBlank(message = "필수 입력 항목입니다.")
        @Size(min = 1, max = 50, message = "크기는 1에서 50 사이입니다.")
        private String phone_num;
    }


    @Getter
    @Setter
    public static class LoginDTO{
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }

    @Getter
    @Setter
    public static class SurveyDTO{
        @NotBlank
        private Long sportsId;

        @NotBlank
        private String usageReason;

        @NotBlank
        private UserSportsLevel level;

        @NotBlank
        private UserSportsGoal goal;
    }
}
