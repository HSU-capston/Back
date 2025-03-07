package capstone.SportyUp.SportyUp_Server.apiPayload.code.status;

import capstone.SportyUp.SportyUp_Server.apiPayload.code.BaseCode;
import capstone.SportyUp.SportyUp_Server.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

    // 카카오 로그인 -> 회원가입 요청
    NEED_TO_ACCEPT_TERMS(HttpStatus.ACCEPTED, "TERM2001", "회원가입을 위해 약관 동의 목록이 추가적으로 필요합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
