package umc.study.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import umc.study.apiPayload.ApiResponse; // ApiResponse 사용
import umc.study.converter.MemberMissionConverter; // Converter 사용
import umc.study.domain.enums.MissionStatus; // MissionStatus Enum 사용
import umc.study.domain.mapping.MemberMission; // MemberMission 엔티티 사용
import umc.study.service.MissionService.MemberMissionQueryService; // 서비스 사용
import umc.study.web.dto.MemberMissionResponseDTO; // 응답 DTO 사용

@RestController
@RequiredArgsConstructor
@RequestMapping("/users") // 사용자 관련 API 경로
public class MemberMissionRestController {

    private final MemberMissionQueryService memberMissionQueryService;

    @GetMapping("/{userId}/missions")
    public ApiResponse<MemberMissionResponseDTO.MissionListDTO> getMyMissions(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "status") MissionStatus status, // Enum으로 직접 받음
            @RequestParam(name = "page", defaultValue = "0") int page) {

        // 서비스 호출 (엔티티 페이지 반환)
        Page<MemberMission> missionPage = memberMissionQueryService.getMyMissionListByStatus(userId, status, page);

        // DTO로 변환
        MemberMissionResponseDTO.MissionListDTO responseDTO = MemberMissionConverter.toMissionListDTO(missionPage);

        // 표준 응답 반환
        return ApiResponse.onSuccess(responseDTO);
    }
}