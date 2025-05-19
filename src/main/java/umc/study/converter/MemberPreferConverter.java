package umc.study.converter;

import umc.study.domain.FoodCategory; // FoodCategory 엔티티 경로 확인
import umc.study.domain.mapping.UserFavorCategory; // UserFavorCategory 엔티티 경로 확인 (강의자료는 MemberPrefer)

import java.util.List;
import java.util.stream.Collectors;

public class MemberPreferConverter {

    public static List<UserFavorCategory> toMemberPreferList(List<FoodCategory> foodCategoryList) {
        return foodCategoryList.stream()
                .map(foodCategory ->
                        UserFavorCategory.builder()
                                .foodCategory(foodCategory)
                                .build()
                ).collect(Collectors.toList());
    }
}