package capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

public class BowlingRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BowlingAnalyzeRequestDTO{

        @NotNull
        File file;
    }
}
