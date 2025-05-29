package capstone.SportyUp.SportyUp_Server.service.UserService;

import capstone.SportyUp.SportyUp_Server.apiPayload.Exception.SportsHandler;
import capstone.SportyUp.SportyUp_Server.apiPayload.Exception.UserHandler;
import capstone.SportyUp.SportyUp_Server.apiPayload.code.status.ErrorStatus;
import capstone.SportyUp.SportyUp_Server.converter.UserConverter;
import capstone.SportyUp.SportyUp_Server.domain.Sports;
import capstone.SportyUp.SportyUp_Server.domain.User;
import capstone.SportyUp.SportyUp_Server.domain.UserSports;
import capstone.SportyUp.SportyUp_Server.domain.enums.Role;
import capstone.SportyUp.SportyUp_Server.domain.enums.UserStatus;
import capstone.SportyUp.SportyUp_Server.repository.SmsVerificationRepository;
import capstone.SportyUp.SportyUp_Server.repository.SportsRepository;
import capstone.SportyUp.SportyUp_Server.repository.UserRepository;
import capstone.SportyUp.SportyUp_Server.repository.UserSportsRepository;
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
    private final SportsRepository sportsRepository;
    private final UserSportsRepository userSportsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public UserResponseDTO.SignUpResultDTO emailSignUp(UserRequestDTO.SingUpDTO request) {
//        //해당 전화번호 인증된 기록이 있는지 확인
//        if(!smsVerificationRepository
//                .findTop1ByPhoneNumAndVerifiedIsTrueAndExpiresAtAfterOrderByCreatedAtDesc(request.getPhoneNum(), LocalDateTime.now())
//                .isPresent()) throw new AuthHandler(ErrorStatus.AUTH_REQUIRED_VERIFICATION);

        //유저 생성
        User newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNum(request.getPhoneNum())
                .birthday(request.getBirthday())
                .status(UserStatus.ACTIVE)
                .isFirstLogin(true) //생성시에 true
                .role(Role.USER)
                .name(request.getNickname())
                .build();

        userRepository.save(newUser);

        //JWT 토큰 발급
        String accessToken = jwtService.createAccessToken(newUser.getId(), newUser.getRole());
        String refreshToken = jwtService.createRefreshToken(newUser.getId());
        //RefreshToken 토큰 저장
        jwtService.saveRefreshToken(newUser, refreshToken);


        return UserConverter.toSignUpResultDTO(accessToken, refreshToken);
    }

    @Override
    public UserResponseDTO.LoginResultDTO emailLogin(UserRequestDTO.LoginDTO request) {
        //사용자 조회
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserHandler(ErrorStatus.USER_EMAIL_NOT_FOUND));

        //비밀번호 검증
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new UserHandler(ErrorStatus.USER_PASSWORD_IS_WRONG);
        }

        //최초 로그인 검사
        boolean showOnboarding = false;
        if(user.isFirstLogin()){
            showOnboarding = true;
            user.setFirstLogin(false);
            userRepository.save(user);
        }

        //Token발급
        String accessToken = jwtService.createAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtService.createRefreshToken(user.getId());

        jwtService.refreshTokenUpdate(user, refreshToken);

        return UserConverter.toLoginResultDTO(accessToken,refreshToken,showOnboarding);
    }

    @Override
    public void surveyUser(Long userId, UserRequestDTO.SurveyDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        Sports sports = sportsRepository.findById(request.getSportsId()).orElseThrow(() -> new SportsHandler(ErrorStatus.SPORTS_NOT_FOUND));

        UserSports userSports = UserSports.builder()
                .user(user)
                .sports(sports)
                .level(request.getLevel())
                .goal(request.getGoal())
                .build();

        userSportsRepository.save(userSports);
    }

    @Override
    public UserResponseDTO.UserInfoDTO updateUserInfo(Long userId, UserRequestDTO.UpdateUserInfoDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        if(request.getName() != null &&
                request.getEmail() != null &&
                request.getPhoneNum() != null){
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhoneNum(request.getPhoneNum());
        }

        return UserConverter.toUserInfoDTO(user);
    }
}
