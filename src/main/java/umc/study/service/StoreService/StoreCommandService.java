package umc.study.service.StoreService;

import umc.study.domain.Mission;
import umc.study.domain.mapping.Review;
import umc.study.web.dto.MissionRequestDTO;
import umc.study.web.dto.ReviewRequestDTO;

public interface StoreCommandService {
    Review addReviewToStore(Long storeId, ReviewRequestDTO.AddReviewDTO request);
    Mission addMissionToStore(Long storeId, MissionRequestDTO.AddMissionDTO request);
}