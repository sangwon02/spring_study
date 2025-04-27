package umc.study.domain;

import jakarta.persistence.*;
import lombok.*;
import umc.study.domain.common.BaseEntity;
import umc.study.domain.mapping.UserFavorCategory;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FoodCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(length = 15)
    private String name;

    // 연관관계 (UserFavorCategory)
    @OneToMany(mappedBy = "foodCategory", cascade = CascadeType.ALL)
    private List<UserFavorCategory> userFavorCategoryList = new ArrayList<>();
}