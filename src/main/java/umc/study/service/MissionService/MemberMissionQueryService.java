package umc.study.service.MissionService;

import org.springframework.data.domain.Page;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.MemberMission;
import umc.study.web.dto.HomeMissionDTO;

public interface MemberMissionQueryService {
    Page<MemberMission> getMyMissionListByStatus(Long userId, MissionStatus status, int page);
    Page<HomeMissionDTO> getChallengingMissionListByAddress(Long userId, String address, int page);
}