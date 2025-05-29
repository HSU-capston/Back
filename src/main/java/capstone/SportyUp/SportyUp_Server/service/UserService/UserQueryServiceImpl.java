package capstone.SportyUp.SportyUp_Server.service.UserService;

import capstone.SportyUp.SportyUp_Server.apiPayload.Exception.UserHandler;
import capstone.SportyUp.SportyUp_Server.apiPayload.code.status.ErrorStatus;
import capstone.SportyUp.SportyUp_Server.converter.UserConverter;
import capstone.SportyUp.SportyUp_Server.domain.User;
import capstone.SportyUp.SportyUp_Server.repository.UserRepository;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    @Override
    public UserResponseDTO.UserInfoDTO getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_EMAIL_NOT_FOUND));

        return UserConverter.toUserInfoDTO(user);
    }
}
