package capstone.SportyUp.SportyUp_Server.converter;

import capstone.SportyUp.SportyUp_Server.web.DTO.AuthDTO.AuthResponseDTO;

public class AuthConverter {
    public static AuthResponseDTO.SmsSendResultDTO toSmsSendResultDTO(){
        return AuthResponseDTO.SmsSendResultDTO.builder()
                .message("문자 전송 완료")
                .build();
    }
}
