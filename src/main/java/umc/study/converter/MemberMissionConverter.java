package umc.study.converter;

import org.springframework.data.domain.Page;
import umc.study.domain.mapping.MemberMission;
import umc.study.web.dto.MemberMissionResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MemberMissionConverter {

    public static MemberMissionResponseDTO.MissionPreviewDTO toMissionPreviewDTO(MemberMission memberMission) {
        return MemberMissionResponseDTO.MissionPreviewDTO.builder()
                .memberMissionId(memberMission.getMemberMissionId())
                .missionSpec(memberMission.getMission().getMissionSpec())
                .point(memberMission.getMission().getPoint())
                .deadline(memberMission.getMission().getDeadline())
                .build();
    }

    public static MemberMissionResponseDTO.MissionListDTO toMissionListDTO(Page<MemberMission> memberMissionPage) {
        List<MemberMissionResponseDTO.MissionPreviewDTO> missionPreviewDTOList = memberMissionPage.getContent().stream()
                .map(MemberMissionConverter::toMissionPreviewDTO)
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

    public static MemberMissionResponseDTO.ChallengeMissionResultDTO toChallengeMissionResultDTO(MemberMission memberMission) {
        return MemberMissionResponseDTO.ChallengeMissionResultDTO.builder()
                .memberMissionId(memberMission.getMemberMissionId())
                .userId(memberMission.getUser().getUserId())
                .missionId(memberMission.getMission().getMissionId())
                .storeId(memberMission.getStore().getStoreId())
                .status(memberMission.getStatus().toString())
                .createdAt(memberMission.getCreatedAt())
                .build();
    }
}