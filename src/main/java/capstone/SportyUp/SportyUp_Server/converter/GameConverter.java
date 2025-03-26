package capstone.SportyUp.SportyUp_Server.converter;

import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameResponseDTO;

public class GameConverter {
    public static GameResponseDTO.CreateResultDTO toCreateResultDTO(Long gameId){
        return GameResponseDTO.CreateResultDTO.builder()
                .id(gameId)
                .build();
    }
}
