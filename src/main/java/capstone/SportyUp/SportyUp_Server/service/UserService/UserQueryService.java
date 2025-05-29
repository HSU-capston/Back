package capstone.SportyUp.SportyUp_Server.service.UserService;

import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserResponseDTO;

public interface UserQueryService {
    UserResponseDTO.UserInfoDTO getUser(Long userId);
}
