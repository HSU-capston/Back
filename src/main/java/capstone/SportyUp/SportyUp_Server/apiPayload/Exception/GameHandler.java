package capstone.SportyUp.SportyUp_Server.apiPayload.Exception;

import capstone.SportyUp.SportyUp_Server.apiPayload.code.BaseErrorCode;

public class GameHandler extends GeneralException{
    public GameHandler(BaseErrorCode code) {
        super(code);
    }
}
