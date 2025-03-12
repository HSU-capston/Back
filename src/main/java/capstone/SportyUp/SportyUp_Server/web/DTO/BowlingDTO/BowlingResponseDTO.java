package capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BowlingResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BowlingAnalyzeResponseDTO{

        @NotNull
        String videoUrl;

    }
}
