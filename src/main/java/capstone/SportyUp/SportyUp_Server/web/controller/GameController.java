package capstone.SportyUp.SportyUp_Server.web.controller;


import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.service.GameService.GameCommandService;
import capstone.SportyUp.SportyUp_Server.service.GameService.GameQueryService;
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

    private final GameCommandService gameCommandService;
    private final GameQueryService gameQueryService;

    @Override
    public ApiResponse<GameResponseDTO.GameInfoListDTO> getGameList(LocalDate date, String sports) {
        return null;
    }

    @Override
    public ApiResponse<GameResponseDTO.GameDateListDTO> getGameDateList(Integer year, Integer month, String sports) {
        return null;
    }

    @Override
    public ApiResponse<GameResponseDTO.GameDetailDTO> getBowlingInfo(Long gameId) {
        return null;
    }

    @Override
    public ApiResponse<GameResponseDTO.CreateResultDTO> createGameInMobile(Long userId, GameRequestDTO.CreateDTO request) {

        return ApiResponse.onSuccess(gameCommandService.createGame(userId, request));
    }

    @Override
    public ApiResponse<GameResponseDTO.EndResultDTO> endGame(Long gameId, GameRequestDTO.EndDTO request) {


        return ApiResponse.onSuccess(gameCommandService.endGame(gameId, request));
    }

    @Override
    public ApiResponse<GameResponseDTO.ChartDTO> getChart(Long userId, Long sportsId) {

        return ApiResponse.onSuccess(gameQueryService.getChart(userId, sportsId));
    }
}
