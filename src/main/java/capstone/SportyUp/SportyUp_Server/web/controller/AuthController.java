package capstone.SportyUp.SportyUp_Server.web.controller;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.service.AuthService.CoolSmsService;
import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthResponseDTO;
import capstone.SportyUp.SportyUp_Server.web.controller.specification.AuthSpecification;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthSpecification {

    private final CoolSmsService coolSmsService;

    @Override
    public ApiResponse<AuthResponseDTO.SmsSendResultDTO> sendSms(AuthRequestDTO.SmsSendDTO request){
        try {
            return ApiResponse.onSuccess(coolSmsService.sendSms(request));
        } catch (CoolsmsException e) {
            throw new RuntimeException(e);
        }
    }
}
