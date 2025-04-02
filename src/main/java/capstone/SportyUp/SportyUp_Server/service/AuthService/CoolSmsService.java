package capstone.SportyUp.SportyUp_Server.service.AuthService;

import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthResponseDTO;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public interface CoolSmsService {
    public AuthResponseDTO.SmsSendResultDTO sendSms(AuthRequestDTO.SmsSendDTO request) throws CoolsmsException;
}
