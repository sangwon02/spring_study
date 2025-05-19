package umc.study.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import umc.study.apiPayload.ApiResponse;
import umc.study.converter.MemberMissionConverter;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.MemberMission;
import umc.study.service.MissionService.MemberMissionQueryService;
import umc.study.service.UserService.UserCommandService; // 경로 및 이름 수정
import umc.study.web.dto.MemberMissionResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberMissionRestController {

    private final MemberMissionQueryService memberMissionQueryService;
    private final UserCommandService userCommandService; // 타입 및 이름 수정

    @GetMapping("/{userId}/missions")
    @Operation(summary = "사용자별 미션 목록 조회 API", description = "특정 사용자의 진행중 또는 완료된 미션 목록을 페이징하여 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청 (예: status 값 오류)")
    })
    @Parameters({
            @Parameter(name = "userId", description = "조회할 사용자의 ID", required = true),
            @Parameter(name = "status", description = "미션 상태 (CHALLENGING 또는 COMPLETE)", required = true),
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0")
    })
    public ApiResponse<MemberMissionResponseDTO.MissionListDTO> getMyMissions(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "status") MissionStatus status,
            @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<MemberMission> missionPage = memberMissionQueryService.getMyMissionListByStatus(userId, status, page);
        return ApiResponse.onSuccess(MemberMissionConverter.toMissionListDTO(missionPage));
    }

    @PostMapping("/{userId}/missions/{missionId}/challenge")
    @Operation(summary = "미션 도전하기 API", description = "특정 사용자가 특정 가게의 미션에 도전합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MISSION4001", description = "미션을 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE4002", description = "미션에 연결된 가게 정보가 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MISSION4003", description = "이미 도전 중이거나 완료한 미션입니다.")
    })
    @Parameters({
            @Parameter(name = "userId", description = "도전할 사용자의 ID", required = true),
            @Parameter(name = "missionId", description = "도전할 미션의 ID", required = true)
    })
    public ApiResponse<MemberMissionResponseDTO.ChallengeMissionResultDTO> challengeMission(
            @PathVariable Long userId,
            @PathVariable Long missionId) {
        MemberMission challengedMission = userCommandService.challengeMission(userId, missionId); // userCommandService 사용
        return ApiResponse.onSuccess(MemberMissionConverter.toChallengeMissionResultDTO(challengedMission));
    }
}
