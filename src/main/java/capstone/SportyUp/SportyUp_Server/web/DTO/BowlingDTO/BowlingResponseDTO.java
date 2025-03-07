package capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

public class BowlingResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BowlingAnalyzeResponseDTO{
        File file;
        Integer score;
    }
}
