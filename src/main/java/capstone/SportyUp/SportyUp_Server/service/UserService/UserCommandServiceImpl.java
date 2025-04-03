package capstone.SportyUp.SportyUp_Server.service.UserService;

import capstone.SportyUp.SportyUp_Server.apiPayload.Exception.AuthHandler;
import capstone.SportyUp.SportyUp_Server.apiPayload.code.status.ErrorStatus;
import capstone.SportyUp.SportyUp_Server.converter.UserConverter;
import capstone.SportyUp.SportyUp_Server.domain.User;
import capstone.SportyUp.SportyUp_Server.domain.enums.UserStatus;
import capstone.SportyUp.SportyUp_Server.repository.SmsVerificationRepository;
import capstone.SportyUp.SportyUp_Server.repository.UserRepository;
import capstone.SportyUp.SportyUp_Server.service.AuthService.JwtService;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    private final SmsVerificationRepository smsVerificationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserResponseDTO.SignUpResultDTO emailSignUp(UserRequestDTO.SingUpDTO request) {
        //해당 전화번호 인증된 기록이 있는지 확인
        if(!smsVerificationRepository
                .findTop1ByPhoneNumAndVerifiedIsTrueOrderByCreatedAtDesc(request.getPhoneNum())
                .isPresent()) throw new AuthHandler(ErrorStatus.AUTH_REQUIRED_VERIFICATION);

        //유저 생성
        User newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNum(request.getPhoneNum())
                .birthday(request.getBirthday())
                .status(UserStatus.ACTIVE)
                .name(request.getNickname())
                .build();

        userRepository.save(newUser);

        //JWT 토큰 발급
        String accessToken = jwtService.createAccessToken(newUser.getId(), newUser.getEmail());
        String refreshToken = jwtService.createRefreshToken(newUser.getId());
        //RefreshToken 토큰 저장
        jwtService.saveRefreshToken(newUser, refreshToken);


        return UserConverter.toSignUpResultDTO(accessToken, refreshToken);
    }
}
