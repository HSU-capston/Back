package capstone.SportyUp.SportyUp_Server.apiPayload.Exception;

import capstone.SportyUp.SportyUp_Server.apiPayload.code.ErrorReasonDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorReasonDTO> handleGeneralException(GeneralException e) {
        return ResponseEntity
                .status(e.getErrorReasonHttpStatus().getHttpStatus())
                .body(e.getErrorReasonHttpStatus());
    }
}