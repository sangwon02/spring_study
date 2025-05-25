package umc.study.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.apiPayload.code.status.ErrorStatus;
import umc.study.apiPayload.exception.handler.StoreHandler; // StoreHandler 사용
import umc.study.domain.Mission;
import umc.study.domain.Store;
import umc.study.repository.MissionRepository.MissionRepository;
import umc.study.repository.StoreRepository.StoreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryServiceImpl implements StoreQueryService{

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;

    @Override
    public Optional<Store> findStore(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public List<Store> findStoresByNameAndScore(String name, Float score) {
        return storeRepository.dynamicQueryWithBooleanBuilder(name, score);
    }

    @Override
    public Page<Mission> getMissionListByStore(Long storeId, int page) {
        // @ExistStore Validator가 가게 존재를 검사합니다.
        storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));
        // 수정된 MissionRepository의 메서드를 사용합니다.
        return missionRepository.findByStore_StoreId(storeId, PageRequest.of(page, 10));
    }
}