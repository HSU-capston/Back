package capstone.SportyUp.SportyUp_Server.web.controller.specification;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

public interface GameSpecification {

    @GetMapping("")
    @Operation(summary = "게임 리스트 조회 API", description = "한 날짜에 진행된 게임들의 리스트를 보여주는 API입니다. QueryString으로 date, sports 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    ApiResponse<GameResponseDTO.GameInfoListDTO> getGameList(@RequestParam LocalDate date, @RequestParam String sports);

    @GetMapping("/dates")
    @Operation(summary = "캘린더 탭 조회 API", description = "달력에서 게임이 진행된 날짜를 표시하기 위한 API입니다. QueryString으로 year와 month, sports 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DATE4001", description = "날짜가 잘못되었습니다.")
    })
    ApiResponse<GameResponseDTO.GameDateListDTO> getGameDateList(@RequestParam Integer year, @RequestParam Integer month, @RequestParam String sports);

    @GetMapping("/{gameId}")
    @Operation(summary = "게임 조회 API", description = "게임 하나를 조회하는 API입니다. PathVariable로 gameId 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "GAME4001", description = "존재하지 않는 게임입니다.")
    })
    ApiResponse<GameResponseDTO.GameDetailDTO> getBowlingInfo(@PathVariable Long gameId);

    @PostMapping("/manual")
    @Operation(summary = "게임 생성 API", description = "모바일 촬영으로 시작 시 게임 생성 API입니다. ")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    ApiResponse<GameResponseDTO.CreateResultDTO> createGameInMobile(@RequestParam Long userId, @RequestBody GameRequestDTO.CreateDTO request);

    @PatchMapping("")
    @Operation(summary = "게임 종료 API", description = "게임 종료 API입니다. 게임 종료 후 점수 입력 등등")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    ApiResponse<GameResponseDTO.EndResultDTO> endGame(@RequestBody GameRequestDTO.EndDTO request);

}
