package capstone.SportyUp.SportyUp_Server.converter;

import capstone.SportyUp.SportyUp_Server.domain.Sports;
import capstone.SportyUp.SportyUp_Server.web.DTO.SportsDTO.SportsResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class SportsConverter {

    public static SportsResponseDTO.SportsDTO toSportsDTO(Sports sports){
        return SportsResponseDTO.SportsDTO.builder()
                .id(sports.getId())
                .name(sports.getName())
                .build();
    }

    public static SportsResponseDTO.SportsListDTO toSportsListDTO(List<Sports> sportsList){

        List<SportsResponseDTO.SportsDTO> sportsListDTO = new ArrayList<>();
        for (Sports sports : sportsList) {
            sportsListDTO.add(toSportsDTO(sports));
        }

        return SportsResponseDTO.SportsListDTO.builder()
                .sportsList(sportsListDTO)
                .build();
    }
}
