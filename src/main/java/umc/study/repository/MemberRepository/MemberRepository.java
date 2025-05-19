package umc.study.repository.MemberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.domain.User; // User 엔티티 경로 확인

public interface MemberRepository extends JpaRepository<User, Long> {
}