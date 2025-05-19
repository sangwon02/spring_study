package umc.study.repository.FoodCategoryRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.domain.FoodCategory; // FoodCategory 엔티티 경로 확인

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}