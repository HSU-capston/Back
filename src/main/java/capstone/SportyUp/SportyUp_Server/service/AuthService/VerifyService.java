package capstone.SportyUp.SportyUp_Server.service.AuthService;

import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthResponseDTO;

public interface VerifyService {
    public AuthResponseDTO.SmsVerifyResultDTO verifySms(AuthRequestDTO.SmsVerifyDTO request);
}
