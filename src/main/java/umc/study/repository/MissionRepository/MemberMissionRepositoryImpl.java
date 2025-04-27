package umc.study.repository.MissionRepository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.MemberMission;
import umc.study.web.dto.HomeMissionDTO;

import java.util.List;

// Q 클래스 import (경로는 프로젝트 구조에 따라 자동으로 생성됨)
import static umc.study.domain.QStore.store;
import static umc.study.domain.mapping.QMemberMission.memberMission;
import static umc.study.domain.QMission.mission;

@RequiredArgsConstructor
public class MemberMissionRepositoryImpl implements MemberMissionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MemberMission> findMemberMissionList(Long userId, MissionStatus status, Pageable pageable) {

        List<MemberMission> content = queryFactory
                .selectFrom(memberMission)
                .join(memberMission.mission, mission).fetchJoin()
                .where(
                        memberMission.user.userId.eq(userId),
                        memberMission.status.eq(status)
                )
                .orderBy(memberMission.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(memberMission.count())
                .from(memberMission)
                .where(
                        memberMission.user.userId.eq(userId),
                        memberMission.status.eq(status)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, (total != null) ? total : 0L);
    }
    @Override
    public Page<HomeMissionDTO> findChallengingMissionsByAddress(Long userId, String address, Pageable pageable) {

        List<HomeMissionDTO> content = queryFactory
                .select(Projections.constructor(HomeMissionDTO.class,
                        memberMission.memberMissionId,
                        mission.missionSpec,
                        mission.point, // DTO 필드 이름과 일치 (completionPoint)
                        mission.deadline,
                        mission.store.name
                ))
                .from(memberMission)
                .join(memberMission.mission, mission)
                .join(mission.store, store) // Mission을 통해 Store 조인 (수정된 엔티티/DDL 기준)
                .where(
                        memberMission.user.userId.eq(userId),
                        memberMission.status.eq(MissionStatus.CHALLENGING),
                        store.address.eq(address) // Store 주소로 필터링
                )
                .orderBy(memberMission.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(memberMission.count())
                .from(memberMission)
                .join(memberMission.mission, mission) // 카운트에도 조인 필요
                .join(mission.store, store)          // 카운트에도 조인 필요
                .where(
                        memberMission.user.userId.eq(userId),
                        memberMission.status.eq(MissionStatus.CHALLENGING),
                        store.address.eq(address)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, (total != null) ? total : 0L);
    }
}