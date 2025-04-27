package umc.study.repository.MissionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.MemberMission;
import umc.study.web.dto.HomeMissionDTO;

public interface MemberMissionRepositoryCustom {

    // 홈 화면용 진행중 미션 목록 조회 메서드
    Page<HomeMissionDTO> findChallengingMissionsByAddress(Long userId, String address, Pageable pageable);

    Page<MemberMission> findMemberMissionList(Long userId, MissionStatus status, Pageable pageable);
}