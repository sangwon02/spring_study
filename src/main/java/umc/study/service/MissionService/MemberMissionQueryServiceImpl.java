package umc.study.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.MemberMission;
import umc.study.repository.MissionRepository.MemberMissionRepository;
import umc.study.web.dto.HomeMissionDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMissionQueryServiceImpl implements MemberMissionQueryService {

    private final MemberMissionRepository memberMissionRepository;

    @Override
    public Page<MemberMission> getMyMissionListByStatus(Long userId, MissionStatus status, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return memberMissionRepository.findMemberMissionList(userId, status, pageable);
    }

    @Override
    public Page<HomeMissionDTO> getChallengingMissionListByAddress(Long userId, String address, int page) {
        Pageable pageable = PageRequest.of(page, 10); // 예: 페이지당 10개
        return memberMissionRepository.findChallengingMissionsByAddress(userId, address, pageable);
    }
}