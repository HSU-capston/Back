package capstone.SportyUp.SportyUp_Server.web.controller.specification;

import capstone.SportyUp.SportyUp_Server.apiPayload.ApiResponse;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.UserDTO.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserSpecification {

    @GetMapping("")
    @Operation(summary = "마이페이지 유저 정보 조회 API", description = "사용자의 정보를 보여주는 API, RequestHeader에 사용자의 정보를 담은 토큰 필요")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4001", description = "사용자가 없습니다.")
    })
    ApiResponse<UserResponseDTO.UserInfoDTO> getUserInfo();

    @PatchMapping("")
    @Operation(summary = "마이페이지 유저 정보 수정 API", description = "사용자 정보를 변경하는 API, RequestBody에 변경된 사용자 정보를 보내주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4001", description = "사용자가 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4002", description = "잘못된 사용자 정보입니다.")
    })
    ApiResponse<UserResponseDTO.UserInfoDTO> updateUserInfo(@RequestBody UserRequestDTO.UpdateUserInfoDTO request);

    @PostMapping("/email")
    @Operation(summary = "이메일 회원가입 API", description = "이메일로 회원가입하는 API, RequestBody에 회원가입에 입력한 사용자 정보를 보내주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4002", description = "잘못된 사용자 정보입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4002", description = "이미 등록된 사용자입니다.")
    })
    ApiResponse<UserResponseDTO.JoinResultDTO> emailSignUp(@RequestBody UserRequestDTO.JoinDTO request);

    @PostMapping("/kakao")
    @Operation(summary = "카카오 회원가입 API", description = "카카오로 회원가입하는 API, RequestBody에 회원가입에 입력한 사용자 정보를 보내주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4002", description = "잘못된 사용자 정보입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4002", description = "이미 등록된 사용자입니다.")
    })
    ApiResponse<UserResponseDTO.JoinResultDTO> kakaoSignUp(@RequestBody UserRequestDTO.JoinDTO request);

    @PostMapping("/naver")
    @Operation(summary = "네이버 회원가입 API", description = "네이버로 회원가입하는 API, RequestBody에 회원가입에 입력한 사용자 정보를 보내주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4002", description = "잘못된 사용자 정보입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4002", description = "이미 등록된 사용자입니다.")
    })
    ApiResponse<UserResponseDTO.JoinResultDTO> naverSignUp(@RequestBody UserRequestDTO.JoinDTO request);



}
