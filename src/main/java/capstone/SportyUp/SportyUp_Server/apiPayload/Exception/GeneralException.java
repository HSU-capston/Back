package capstone.SportyUp.SportyUp_Server.apiPayload.Exception;

import capstone.SportyUp.SportyUp_Server.apiPayload.code.BaseErrorCode;
import capstone.SportyUp.SportyUp_Server.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
