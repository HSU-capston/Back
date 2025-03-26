package capstone.SportyUp.SportyUp_Server.web.controller;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.web.DTO.SportsDTO.SportsResponseDTO;
import capstone.SportyUp.SportyUp_Server.web.controller.specification.SportsSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sports")
public class SportsController implements SportsSpecification {

    @Override
    public ApiResponse<SportsResponseDTO.SportsListDTO> getSportsList() {
        return null;
    }
}
