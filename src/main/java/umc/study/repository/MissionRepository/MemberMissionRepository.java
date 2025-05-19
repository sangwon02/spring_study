package umc.study.repository.MissionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.domain.Mission; // Mission 임포트 추가
import umc.study.domain.User;    // User 임포트 추가
import umc.study.domain.mapping.MemberMission;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long>, MemberMissionRepositoryCustom {

    // 특정 사용자가 특정 미션을 이미 수행 중이거나 완료했는지 확인하는 메서드
    boolean existsByUserAndMission(User user, Mission mission);

}
