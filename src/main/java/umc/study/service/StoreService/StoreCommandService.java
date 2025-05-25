package umc.study.service.StoreService;

import umc.study.domain.Mission;
import umc.study.web.dto.MissionRequestDTO;

public interface StoreCommandService {
    Mission addMissionToStore(Long storeId, MissionRequestDTO.AddMissionDTO request);
}