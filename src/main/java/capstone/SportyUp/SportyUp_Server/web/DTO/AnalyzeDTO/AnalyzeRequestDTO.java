package capstone.SportyUp.SportyUp_Server.web.DTO.AnalyzeDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class AnalyzeRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BowlingDTO{
        @NotNull
        MultipartFile file;
    }
}
