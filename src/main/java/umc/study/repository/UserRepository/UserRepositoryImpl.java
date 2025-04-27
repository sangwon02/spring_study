package umc.study.repository.UserRepository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import umc.study.web.dto.MyPageInfoDTO; // DTO 임포트

import java.util.Optional;

import static umc.study.domain.QUser.user; // QUser 임포트

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MyPageInfoDTO> findMyPageInfoById(Long userId) {
        MyPageInfoDTO result = queryFactory
                .select(Projections.constructor(MyPageInfoDTO.class,
                        user.name,
                        user.email,
                        user.phonenumber,
                        user.point
                ))
                .from(user)
                .where(user.userId.eq(userId)) // 특정 사용자 ID 조건
                .fetchOne(); // 단 건 조회 (없으면 null 반환)

        // 조회 결과가 null일 수 있으므로 Optional로 감싸서 반환
        return Optional.ofNullable(result);
    }
}