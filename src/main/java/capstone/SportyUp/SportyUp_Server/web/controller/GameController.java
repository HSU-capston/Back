package capstone.SportyUp.SportyUp_Server.web.controller;


import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameResponseDTO;
import capstone.SportyUp.SportyUp_Server.web.controller.specification.GameSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController implements GameSpecification {

    @Override
    public ApiResponse<GameResponseDTO.GameInfoListDTO> getGameList(LocalDate date) {
        return null;
    }

    @Override
    public ApiResponse<GameResponseDTO.GameDateListDTO> getGameDateList(Integer year, Integer month) {
        return null;
    }

    @Override
    public ApiResponse<GameResponseDTO.BowlingInfoDTO> getBowlingInfo(Long gameId) {
        return null;
    }

    @Override
    public ApiResponse<GameResponseDTO.CreateResultDTO> createGameInMobile(GameRequestDTO.CreateDTO request) {
        return null;
    }

    @Override
    public ApiResponse<GameResponseDTO.EndResultDTO> endGame(GameRequestDTO.EndDTO request) {
        return null;
    }
}
