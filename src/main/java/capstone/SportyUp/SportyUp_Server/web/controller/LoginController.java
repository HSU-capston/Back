package capstone.SportyUp.SportyUp_Server.web.controller;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.service.UserService.UserCommandService;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserResponseDTO;
import capstone.SportyUp.SportyUp_Server.web.controller.specification.LoginSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController implements LoginSpecification {
    private final UserCommandService userCommandService;

    @Override
    public ApiResponse<UserResponseDTO.LoginResultDTO> emailLogin(UserRequestDTO.LoginDTO request) {

        return ApiResponse.onSuccess(userCommandService.emailLogin(request));
    }
}
