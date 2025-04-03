package capstone.SportyUp.SportyUp_Server.apiPayload.Exception;

import capstone.SportyUp.SportyUp_Server.apiPayload.code.BaseErrorCode;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode code) {
        super(code);
    }
}
