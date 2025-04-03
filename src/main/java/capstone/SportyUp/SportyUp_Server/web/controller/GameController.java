package capstone.SportyUp.SportyUp_Server.web.controller;


import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.service.GameService.GameCommandService;
import capstone.SportyUp.SportyUp_Server.service.GameService.GameQueryService;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameResponseDTO;
import capstone.SportyUp.SportyUp_Server.web.controller.specification.GameSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ApiResponse<GameResponseDTO.GameInfoListDTO> getGameListAllCategory(LocalDate date) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        return ApiResponse.onSuccess(gameQueryService.getGameListAllCategory(userId, date));
    }

    @Override
    public ApiResponse<GameResponseDTO.GameInfoListDTO> getGameListOneCategory(LocalDate date, Long sportsId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        return ApiResponse.onSuccess(gameQueryService.getGameListOneCategory(userId, date, sportsId));
    }

    @Override
    public ApiResponse<GameResponseDTO.GameDateListDTO> getGameDateListAllCategory(Integer year, Integer month) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        return ApiResponse.onSuccess(gameQueryService.getGameDateListAllCategory(userId,year,month));
    }

    @Override
    public ApiResponse<GameResponseDTO.GameDateListDTO> getGameDateListOneCategory(Integer year, Integer month, Long sportsId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        return ApiResponse.onSuccess(gameQueryService.getGameDateListOneCategory(userId,year,month,sportsId));
    }

    @Override
    public ApiResponse<GameResponseDTO.GameDetailDTO> getGameDetail(Long gameId) {

        return ApiResponse.onSuccess(gameQueryService.getGameDetail(gameId));
    }

    @Override
    public ApiResponse<GameResponseDTO.CreateResultDTO> createGameInMobile(GameRequestDTO.CreateDTO request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        return ApiResponse.onSuccess(gameCommandService.createGame(userId, request));
    }

    @Override
    public ApiResponse<GameResponseDTO.EndResultDTO> endGame(Long gameId, GameRequestDTO.EndDTO request) {


        return ApiResponse.onSuccess(gameCommandService.endGame(gameId, request));
    }

    @Override
    public ApiResponse<GameResponseDTO.ChartDTO> getChart(Long sportsId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        return ApiResponse.onSuccess(gameQueryService.getChart(userId, sportsId));
    }
}
