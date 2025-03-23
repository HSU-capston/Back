package capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public class UserRequestDTO {

    @Getter
    @Setter
    public static class JoinDTO{
        @NotBlank
        private String email;
        @NotBlank
        private String password;
        @NotBlank
        private String name;
        @NotBlank
        private String phone_num;
        @NotBlank
        private String prefer_sports;
        @NotBlank
        private String level;
        @NotBlank
        private String goal;
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
}
