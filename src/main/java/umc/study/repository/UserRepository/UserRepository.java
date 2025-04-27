package umc.study.repository.UserRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.domain.User; // User 엔티티 임포트

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}