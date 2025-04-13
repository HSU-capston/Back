package capstone.SportyUp.SportyUp_Server.apiPayload.Exception;

import capstone.SportyUp.SportyUp_Server.apiPayload.code.BaseErrorCode;

public class AnalyzeHandler extends GeneralException {
    public AnalyzeHandler(BaseErrorCode code) {
        super(code);
    }
}