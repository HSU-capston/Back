package capstone.SportyUp.SportyUp_Server.converter;

import capstone.SportyUp.SportyUp_Server.web.DTO.BowlingDTO.BowlingResponseDTO;

public class BowlingConverter {
    public static BowlingResponseDTO.BowlingAnalyzeResponseDTO toBowlingAnalyzeResponseDTO(String videoUrl){

        return BowlingResponseDTO.BowlingAnalyzeResponseDTO.builder()
                .videoUrl(videoUrl)
                .build();
    }
}
