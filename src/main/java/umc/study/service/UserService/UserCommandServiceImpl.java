package umc.study.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.apiPayload.code.status.ErrorStatus;
import umc.study.apiPayload.exception.GeneralException;
import umc.study.converter.MemberConverter;
import umc.study.converter.MemberPreferConverter;
import umc.study.domain.FoodCategory;
import umc.study.domain.Mission;
import umc.study.domain.Store;
import umc.study.domain.User;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.MemberMission;
import umc.study.domain.mapping.UserFavorCategory;
import umc.study.repository.FoodCategoryRepository.FoodCategoryRepository;
import umc.study.repository.MissionRepository.MemberMissionRepository;
import umc.study.repository.MissionRepository.MissionRepository;
import umc.study.repository.UserRepository.UserRepository;
import umc.study.web.dto.MemberRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional // 클래스 레벨에 @Transactional을 붙이면 모든 public 메서드에 적용됩니다.
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    @Override
    public User joinUser(MemberRequestDTO.JoinDto request) {
        User newUser = MemberConverter.toUser(request);
        List<Long> preferCategoryIds = request.getPreferCategory();
        if (preferCategoryIds != null && !preferCategoryIds.isEmpty()) {
            List<FoodCategory> foodCategoryList = preferCategoryIds.stream()
                    .map(categoryId -> foodCategoryRepository.findById(categoryId)
                            .orElseThrow(() -> new GeneralException(ErrorStatus.FOOD_CATEGORY_NOT_FOUND)))
                    .collect(Collectors.toList());
            List<UserFavorCategory> userFavorCategoryList = MemberPreferConverter.toMemberPreferList(foodCategoryList);
            userFavorCategoryList.forEach(userFavorCategory -> userFavorCategory.setUser(newUser));
        }
        return userRepository.save(newUser);
    }

    @Override
    public MemberMission challengeMission(Long userId, Long missionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MISSION_NOT_FOUND));
        Store store = mission.getStore(); // Mission 엔티티에 getStore()가 있다고 가정
        if (store == null) {
            // Mission 엔티티 설계상 store가 null일 수 없도록 NOT NULL 제약조건이 있다면 이 검사는 불필요
            // 하지만 DDL에서 Mission의 store_id가 NOT NULL이므로, mission.getStore()는 null을 반환하지 않아야 정상
            throw new GeneralException(ErrorStatus.STORE_NOT_LINKED_TO_MISSION);
        }

        // MemberMissionRepository에 해당 메서드가 정의되어 있어야 함
        boolean alreadyExists = memberMissionRepository.existsByUserAndMission(user, mission);
        if (alreadyExists) {
            throw new GeneralException(ErrorStatus.MISSION_ALREADY_ATTEMPTED);
        }

        MemberMission newMemberMission = MemberMission.builder()
                .user(user)
                .mission(mission)
                .store(store) // MemberMission에 Store도 직접 연관관계로 설정
                .status(MissionStatus.CHALLENGING)
                .build();
        return memberMissionRepository.save(newMemberMission);
    }
}
