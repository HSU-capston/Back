package capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO;

import lombok.Getter;
import lombok.Setter;

public class GameRequestDTO {

    @Getter
    @Setter
    public static class CreateDTO{
        String sports;
    }

    @Getter
    @Setter
    public static class EndDTO{
        Integer score;
    }
}
