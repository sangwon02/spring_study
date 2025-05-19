package umc.study.service.UserService;

import umc.study.domain.User;
import umc.study.domain.mapping.MemberMission;
import umc.study.web.dto.MemberRequestDTO;

public interface UserCommandService {
    User joinUser(MemberRequestDTO.JoinDto request);
    MemberMission challengeMission(Long userId, Long missionId);
}
