package capstone.SportyUp.SportyUp_Server.converter;

import capstone.SportyUp.SportyUp_Server.domain.User;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserResponseDTO;

public class UserConverter {
    public static UserResponseDTO.SignUpResultDTO toSignUpResultDTO(String accessToken, String refreshToken) {
        return UserResponseDTO.SignUpResultDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static UserResponseDTO.LoginResultDTO toLoginResultDTO(String accessToken, String refreshToken, boolean showOnboarding) {
        return UserResponseDTO.LoginResultDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .showOnboarding(showOnboarding)
                .build();
    }

    public static UserResponseDTO.UserInfoDTO toUserInfoDTO(User user) {
        return UserResponseDTO.UserInfoDTO.builder()
                .email(user.getEmail())
                .phoneNum(user.getPhoneNum())
                .name(user.getName())
                .build();
    }
}
