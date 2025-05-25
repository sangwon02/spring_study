package umc.study.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.study.apiPayload.ApiResponse;
import umc.study.converter.MemberMissionConverter;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.MemberMission;
import umc.study.service.MissionService.MemberMissionQueryService;
import umc.study.service.UserService.UserCommandService;
import umc.study.validation.annotation.CheckPage;
import umc.study.validation.annotation.ExistMission;
import umc.study.validation.annotation.ExistUser;
import umc.study.web.dto.MemberMissionResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class MemberMissionRestController {

    private final MemberMissionQueryService memberMissionQueryService;
    private final UserCommandService userCommandService;

    @GetMapping("/{userId}/missions")
    @Operation(summary = "사용자별 미션 목록 조회 API", description = "특정 사용자의 진행중 또는 완료된 미션 목록을 페이징하여 조회합니다. '내가 진행중인 미션 목록'은 status=CHALLENGING으로 호출하세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request (페이지 번호 오류, status 값 오류)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not Found (사용자 없음)")
    })
    @Parameters({
            @Parameter(name = "userId", description = "조회할 사용자의 ID", required = true),
            @Parameter(name = "status", description = "미션 상태 (CHALLENGING 또는 COMPLETE)", required = true),
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", example = "1", required = true)
    })
    public ApiResponse<MemberMissionResponseDTO.MissionListDTO> getMyMissions(
            @ExistUser @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "status") MissionStatus status, // Enum 타입으로 직접 받음
            @CheckPage @RequestParam(name = "page") int page) {
        Page<MemberMission> missionPage = memberMissionQueryService.getMyMissionListByStatus(userId, status, page - 1);
        return ApiResponse.onSuccess(MemberMissionConverter.toMissionListDTO(missionPage));
    }

    @PostMapping("/{userId}/missions/{missionId}/challenge")
    @Operation(summary = "미션 도전하기 API", description = "특정 사용자가 특정 가게의 미션에 도전합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not Found (사용자/미션 없음)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request (미션에 연결된 가게 정보 없음, 이미 도전/완료한 미션)")
    })
    @Parameters({
            @Parameter(name = "userId", description = "도전할 사용자의 ID", required = true),
            @Parameter(name = "missionId", description = "도전할 미션의 ID", required = true)
    })
    public ApiResponse<MemberMissionResponseDTO.ChallengeMissionResultDTO> challengeMission(
            @ExistUser @PathVariable Long userId,
            @ExistMission @PathVariable Long missionId) {
        MemberMission challengedMission = userCommandService.challengeMission(userId, missionId);
        return ApiResponse.onSuccess(MemberMissionConverter.toChallengeMissionResultDTO(challengedMission));
    }
}