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
        private String phone_num;
    }
}
