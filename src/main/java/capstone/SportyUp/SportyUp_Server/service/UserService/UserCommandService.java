package capstone.SportyUp.SportyUp_Server.service.UserService;

import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserResponseDTO;

public interface UserCommandService {
    public UserResponseDTO.SignUpResultDTO emailSignUp(UserRequestDTO.SingUpDTO request);
    public UserResponseDTO.LoginResultDTO emailLogin(UserRequestDTO.LoginDTO request);
    public void surveyUser(Long userId, UserRequestDTO.SurveyDTO request);
    public UserResponseDTO.UserInfoDTO updateUserInfo(Long userId, UserRequestDTO.UpdateUserInfoDTO request);
}
