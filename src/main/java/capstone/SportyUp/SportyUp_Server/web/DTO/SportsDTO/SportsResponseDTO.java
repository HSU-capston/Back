package capstone.SportyUp.SportyUp_Server.web.DTO.SportsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SportsResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SportsListDTO {
        List<SportsDTO> sportsList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SportsDTO{
        Long id;
        String name;
    }

}
