package umc.study.converter;

import org.springframework.data.domain.Page;
import umc.study.domain.Mission;
import umc.study.domain.Store;
import umc.study.web.dto.MissionRequestDTO;
import umc.study.web.dto.MissionResponseDTO;
import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    public static MissionResponseDTO.AddMissionResultDTO toAddMissionResultDTO(Mission mission) {
        return MissionResponseDTO.AddMissionResultDTO.builder()
                .missionId(mission.getMissionId())
                .storeId(mission.getStore().getStoreId())
                .createdAt(mission.getCreatedAt())
                .build();
    }

    public static Mission toMission(MissionRequestDTO.AddMissionDTO request, Store store) {
        return Mission.builder()
                .missionSpec(request.getMissionSpec())
                .point(request.getPoint())
                .deadline(request.getDeadline())
                .store(store)
                .build();
    }

    public static MissionResponseDTO.MissionPreviewDTO toMissionPreviewDTO(Mission mission) {
        return MissionResponseDTO.MissionPreviewDTO.builder()
                .missionId(mission.getMissionId())
                .missionSpec(mission.getMissionSpec())
                .point(mission.getPoint())
                .deadline(mission.getDeadline())
                .build();
    }

    public static MissionResponseDTO.MissionPreviewListDTO toMissionPreviewListDTO(Page<Mission> missionPage) {
        List<MissionResponseDTO.MissionPreviewDTO> missionDTOList = missionPage.getContent().stream()
                .map(MissionConverter::toMissionPreviewDTO).collect(Collectors.toList());

        return MissionResponseDTO.MissionPreviewListDTO.builder()
                .missionList(missionDTOList)
                .listSize(missionDTOList.size())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .isFirst(missionPage.isFirst())
                .isLast(missionPage.isLast())
                .build();
    }
}