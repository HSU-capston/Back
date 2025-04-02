package capstone.SportyUp.SportyUp_Server.apiPayload.Exception;

import capstone.SportyUp.SportyUp_Server.apiPayload.code.BaseErrorCode;

public class AuthHandler extends GeneralException {
    public AuthHandler(BaseErrorCode code) {
        super(code);
    }
}