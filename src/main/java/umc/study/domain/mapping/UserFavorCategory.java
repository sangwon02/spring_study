package umc.study.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import umc.study.domain.FoodCategory;
import umc.study.domain.User;
import umc.study.domain.common.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserFavorCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userFavorId; // PK 필드명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // nullable = false 추가
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false) // nullable = false 추가
    private FoodCategory foodCategory;

    // 연관관계 편의 메서드 (양방향 관계 설정 시 필요)
    public void setUser(User user) {
        // 기존 User와의 연관관계가 있다면 제거 (JPA의 생명주기 관리 방식에 따라 선택적)
        if (this.user != null) {
            this.user.getUserFavorCategoryList().remove(this);
        }
        this.user = user; // 현재 UserFavorCategory의 user 필드 설정
        // User 엔티티의 userFavorCategoryList에도 현재 객체 추가 (양방향 연관관계 설정)
        if (user != null && !user.getUserFavorCategoryList().contains(this)) { // 중복 추가 방지
            user.getUserFavorCategoryList().add(this);
        }
    }

    // FoodCategory 설정 메서드 (필요시)
    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }
}
