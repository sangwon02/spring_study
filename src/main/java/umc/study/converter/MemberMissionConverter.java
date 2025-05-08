package umc.study.converter;

import org.springframework.data.domain.Page;
import umc.study.domain.mapping.MemberMission; // 엔티티 경로 확인
import umc.study.web.dto.MemberMissionResponseDTO; // DTO 경로 확인

import java.util.List;
import java.util.stream.Collectors;

public class MemberMissionConverter {

    // 단일 MemberMission 엔티티 -> MissionPreviewDTO 변환
    public static MemberMissionResponseDTO.MissionPreviewDTO toMissionPreviewDTO(MemberMission memberMission) {
        return MemberMissionResponseDTO.MissionPreviewDTO.builder()
                .memberMissionId(memberMission.getMemberMissionId())
                .missionSpec(memberMission.getMission().getMissionSpec()) // 연관된 Mission 엔티티 정보 사용
                .point(memberMission.getMission().getPoint())
                .deadline(memberMission.getMission().getDeadline())
                // .storeName(memberMission.getStore().getName()) // Store 정보 필요시 추가 (엔티티/fetch join 확인 필요)
                .build();
    }

    // Page<MemberMission> -> MissionListDTO 변환
    public static MemberMissionResponseDTO.MissionListDTO toMissionListDTO(Page<MemberMission> memberMissionPage) {
        List<MemberMissionResponseDTO.MissionPreviewDTO> missionPreviewDTOList = memberMissionPage.getContent().stream()
                .map(MemberMissionConverter::toMissionPreviewDTO) // 위 메서드 재사용
                .collect(Collectors.toList());

        return MemberMissionResponseDTO.MissionListDTO.builder()
                .isLast(memberMissionPage.isLast())
                .isFirst(memberMissionPage.isFirst())
                .totalPage(memberMissionPage.getTotalPages())
                .totalElements(memberMissionPage.getTotalElements())
                .listSize(missionPreviewDTOList.size())
                .missionList(missionPreviewDTOList)
                .build();
    }
}