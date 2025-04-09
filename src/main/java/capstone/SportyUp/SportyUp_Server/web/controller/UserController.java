package capstone.SportyUp.SportyUp_Server.web.controller;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.service.UserService.UserCommandService;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserResponseDTO;
import capstone.SportyUp.SportyUp_Server.web.controller.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserSpecification {
    private final UserCommandService userCommandService;
    @Override
    public ApiResponse<UserResponseDTO.UserInfoDTO> getUserInfo() {
        return null;
    }

    @Override
    public ApiResponse<UserResponseDTO.UserInfoDTO> updateUserInfo(UserRequestDTO.UpdateUserInfoDTO request) {
        return null;
    }

    @Override
    public ApiResponse<UserResponseDTO.SignUpResultDTO> emailSignUp(UserRequestDTO.SingUpDTO request) {

        return ApiResponse.onSuccess(userCommandService.emailSignUp(request));
    }

    @Override
    public ApiResponse<UserResponseDTO.SignUpResultDTO> kakaoSignUp(UserRequestDTO.SingUpDTO request) {
        return null;
    }

    @Override
    public ApiResponse<UserResponseDTO.SignUpResultDTO> naverSignUp(UserRequestDTO.SingUpDTO request) {
        return null;
    }

    @Override
    public ApiResponse surveyUser(UserRequestDTO.SurveyDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        userCommandService.surveyUser(userId, request);

        return ApiResponse.onSuccess();
    }
}
