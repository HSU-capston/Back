package capstone.SportyUp.SportyUp_Server.apiPayload.Exception;

import capstone.SportyUp.SportyUp_Server.apiPayload.code.BaseErrorCode;

public class SportsHandler extends GeneralException {
    public SportsHandler(BaseErrorCode code) {
        super(code);
    }
}
