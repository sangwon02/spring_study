package umc.study.converter;

import umc.study.domain.Mission;
import umc.study.domain.Store;
import umc.study.web.dto.MissionRequestDTO;
import umc.study.web.dto.MissionResponseDTO;

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
}