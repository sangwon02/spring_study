package umc.study.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final PasswordEncoder passwordEncoder; // 1. PasswordEncoder 의존성 주입 추가

    @Override
    public User joinUser(MemberRequestDTO.JoinDto request) {
        User newUser = MemberConverter.toUser(request);

        // 2. 비밀번호 암호화 로직 추가
        newUser.encodePassword(passwordEncoder.encode(request.getPassword()));

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
        Store store = mission.getStore();
        if (store == null) {
            throw new GeneralException(ErrorStatus.STORE_NOT_LINKED_TO_MISSION);
        }

        boolean alreadyExists = memberMissionRepository.existsByUserAndMission(user, mission);
        if (alreadyExists) {
            throw new GeneralException(ErrorStatus.MISSION_ALREADY_ATTEMPTED);
        }

        MemberMission newMemberMission = MemberMission.builder()
                .user(user)
                .mission(mission)
                .store(store)
                .status(MissionStatus.CHALLENGING)
                .build();
        return memberMissionRepository.save(newMemberMission);
    }
}