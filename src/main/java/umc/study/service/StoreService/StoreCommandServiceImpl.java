package umc.study.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.apiPayload.code.status.ErrorStatus;
import umc.study.apiPayload.exception.GeneralException;
import umc.study.converter.MissionConverter;
import umc.study.converter.ReviewConverter;
import umc.study.domain.Mission;
import umc.study.domain.Store;
import umc.study.domain.User;
import umc.study.domain.mapping.Review;
import umc.study.repository.MissionRepository.MissionRepository;
import umc.study.repository.ReviewRepository.ReviewRepository;
import umc.study.repository.StoreRepository.StoreRepository;
import umc.study.repository.UserRepository.UserRepository;
import umc.study.web.dto.MissionRequestDTO;
import umc.study.web.dto.ReviewRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final MissionRepository missionRepository;

    @Override
    public Review addReviewToStore(Long storeId, ReviewRequestDTO.AddReviewDTO request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.STORE_NOT_FOUND));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Review newReview = ReviewConverter.toReview(request, user, store);
        return reviewRepository.save(newReview);
    }

    @Override
    public Mission addMissionToStore(Long storeId, MissionRequestDTO.AddMissionDTO request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.STORE_NOT_FOUND));
        Mission newMission = MissionConverter.toMission(request, store);
        return missionRepository.save(newMission);
    }
}