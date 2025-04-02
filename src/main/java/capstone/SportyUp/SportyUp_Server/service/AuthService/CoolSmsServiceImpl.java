package capstone.SportyUp.SportyUp_Server.service.AuthService;

import capstone.SportyUp.SportyUp_Server.converter.AuthConverter;
import capstone.SportyUp.SportyUp_Server.domain.SmsVerification;
import capstone.SportyUp.SportyUp_Server.repository.SmsVerificationRepository;
import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CoolSmsServiceImpl implements CoolSmsService {
    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.api.number}")
    private String fromPhoneNumber;

    private final SmsVerificationRepository smsVerificationRepository;

    public AuthResponseDTO.SmsSendResultDTO sendSms(AuthRequestDTO.SmsSendDTO request) throws CoolsmsException {
        // 랜덤한 4자리 인증번호 생성
        String numStr = generateRandomNumber();
        String userPhoneNum = request.getPhoneNum();

        try {
            Message coolsms = new Message(apiKey, apiSecret); // 생성자를 통해 API 키와 API 시크릿 전달

            HashMap<String, String> params = new HashMap<>();
            params.put("to", userPhoneNum);    // 수신 전화번호
            params.put("from", fromPhoneNumber);    // 발신 전화번호
            params.put("type", "sms");
            params.put("text", "[SportyUP]\n 인증번호 : " + numStr);

            // 메시지 전송
            coolsms.send(params);

        } catch (Exception e) {
            throw new CoolsmsException("Failed to send SMS", e.hashCode());
        }

        SmsVerification smsVerification = SmsVerification.builder()
                .phoneNum(userPhoneNum)
                .verificationCode(numStr)
                .expiresAt(LocalDateTime.now().plusMinutes(3))  //현재부터 3분뒤 만료
                .build(); // 생성된 인증번호 반환

        smsVerificationRepository.save(smsVerification);

        return AuthConverter.toSmsSendResultDTO(smsVerification);
    }

    // 랜덤한 4자리 숫자 생성 메서드
    private String generateRandomNumber() {
        Random rand = new Random();
        StringBuilder numStr = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            numStr.append(rand.nextInt(10));
        }
        return numStr.toString();
    }

}
