package umc.study.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.apiPayload.code.status.ErrorStatus;
import umc.study.apiPayload.exception.handler.UserHandler;
import umc.study.config.security.jwt.JwtTokenProvider;
import umc.study.converter.MemberConverter;
import umc.study.converter.MemberPreferConverter;
import umc.study.domain.FoodCategory;
import umc.study.domain.Mission;
import umc.study.domain.User;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.MemberMission;
import umc.study.domain.mapping.UserFavorCategory;
import umc.study.repository.FoodCategoryRepository.FoodCategoryRepository;
import umc.study.repository.MissionRepository.MemberMissionRepository;
import umc.study.repository.MissionRepository.MissionRepository;
import umc.study.repository.UserRepository.UserRepository;
import umc.study.web.dto.MemberRequestDTO;
import umc.study.web.dto.MemberResponseDTO;

import java.util.Collections;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public User joinUser(MemberRequestDTO.JoinDto request) {
        User newUser = MemberConverter.toUser(request);
        newUser.encodePassword(passwordEncoder.encode(request.getPassword()));
        List<Long> preferCategoryIds = request.getPreferCategory();
        if (preferCategoryIds != null && !preferCategoryIds.isEmpty()) {
            List<FoodCategory> foodCategoryList = foodCategoryRepository.findAllById(preferCategoryIds);
            List<UserFavorCategory> userFavorCategoryList = MemberPreferConverter.toMemberPreferList(foodCategoryList);
            userFavorCategoryList.forEach(userFavorCategory -> userFavorCategory.setUser(newUser));
        }
        return userRepository.save(newUser);
    }

    @Override
    public MemberResponseDTO.LoginResultDTO loginMember(MemberRequestDTO.LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserHandler(ErrorStatus.INVALID_PASSWORD);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null,
                Collections.singleton(() -> "ROLE_" + user.getRole().name())
        );

        String accessToken = jwtTokenProvider.generateToken(authentication);

        return MemberConverter.toLoginResultDTO(
                user.getUserId(),
                accessToken
        );
    }

    @Override
    public MemberMission challengeMission(Long userId, Long missionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MISSION_NOT_FOUND));

        boolean alreadyExists = memberMissionRepository.existsByUserAndMission(user, mission);
        if (alreadyExists) {
            throw new UserHandler(ErrorStatus.MISSION_ALREADY_ATTEMPTED);
        }

        MemberMission newMemberMission = MemberMission.builder()
                .user(user)
                .mission(mission)
                .status(MissionStatus.CHALLENGING)
                .build();
        return memberMissionRepository.save(newMemberMission);
    }
}