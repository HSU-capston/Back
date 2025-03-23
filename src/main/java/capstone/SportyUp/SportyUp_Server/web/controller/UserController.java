package capstone.SportyUp.SportyUp_Server.web.controller;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserResponseDTO;
import capstone.SportyUp.SportyUp_Server.web.controller.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserSpecification {
    @Override
    public ApiResponse<UserResponseDTO.UserInfoDTO> getUserInfo() {
        return null;
    }

    @Override
    public ApiResponse<UserResponseDTO.UserInfoDTO> updateUserInfo(UserRequestDTO.UpdateUserInfoDTO request) {
        return null;
    }

    @Override
    public ApiResponse<UserResponseDTO.JoinResultDTO> emailSignUp(UserRequestDTO.JoinDTO request) {
        return null;
    }

    @Override
    public ApiResponse<UserResponseDTO.JoinResultDTO> kakaoSignUp(UserRequestDTO.JoinDTO request) {
        return null;
    }

    @Override
    public ApiResponse<UserResponseDTO.JoinResultDTO> naverSignUp(UserRequestDTO.JoinDTO request) {
        return null;
    }
}
