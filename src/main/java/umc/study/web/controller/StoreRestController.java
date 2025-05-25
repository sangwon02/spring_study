package umc.study.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.study.apiPayload.ApiResponse;
import umc.study.converter.MissionConverter;
import umc.study.domain.Mission;
import umc.study.service.StoreService.StoreCommandService;
import umc.study.service.StoreService.StoreQueryService;
import umc.study.validation.annotation.CheckPage;
import umc.study.validation.annotation.ExistStore;
import umc.study.web.dto.MissionRequestDTO;
import umc.study.web.dto.MissionResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/stores")
public class StoreRestController {

    private final StoreCommandService storeCommandService;
    private final StoreQueryService storeQueryService;

    @PostMapping("/{storeId}/missions")
    @Operation(summary = "가게에 미션 추가 API", description = "특정 가게에 새로운 미션을 등록합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request (입력값 오류 등)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not Found (가게 없음)")
    })
    @Parameters({
            @Parameter(name = "storeId", description = "미션을 추가할 가게의 아이디", required = true)
    })
    public ApiResponse<MissionResponseDTO.AddMissionResultDTO> addMission(
            @ExistStore @PathVariable Long storeId,
            @RequestBody @Valid MissionRequestDTO.AddMissionDTO request) {
        Mission newMission = storeCommandService.addMissionToStore(storeId, request);
        return ApiResponse.onSuccess(MissionConverter.toAddMissionResultDTO(newMission));
    }

    @GetMapping("/{storeId}/missions")
    @Operation(summary = "특정 가게의 미션 목록 조회 API", description = "특정 가게의 미션 목록을 페이징하여 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request (페이지 번호 오류)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Not Found (가게 없음)")
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디", required = true),
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", required = true)
    })
    public ApiResponse<MissionResponseDTO.MissionPreviewListDTO> getStoreMissions(
            @ExistStore @PathVariable Long storeId,
            @CheckPage @RequestParam Integer page) {
        Page<Mission> missionPage = storeQueryService.getMissionListByStore(storeId, page - 1);
        return ApiResponse.onSuccess(MissionConverter.toMissionPreviewListDTO(missionPage));
    }
}