package umc.study.repository.MissionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.domain.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    Page<Mission> findByStore_StoreId(Long storeId, Pageable pageable); // Store 엔티티 내부의 storeId 필드 사용
}