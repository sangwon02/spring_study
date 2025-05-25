package umc.study.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.apiPayload.code.status.ErrorStatus;
import umc.study.apiPayload.exception.handler.StoreHandler;
import umc.study.converter.MissionConverter;
import umc.study.domain.Mission;
import umc.study.domain.Store;
import umc.study.repository.MissionRepository.MissionRepository;
import umc.study.repository.StoreRepository.StoreRepository;
import umc.study.web.dto.MissionRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;

    @Override
    public Mission addMissionToStore(Long storeId, MissionRequestDTO.AddMissionDTO request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));
        Mission newMission = MissionConverter.toMission(request, store);
        return missionRepository.save(newMission);
    }
}