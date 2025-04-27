package umc.study.domain.mapping; // 경로 확인

import jakarta.persistence.*;
import lombok.*;
import umc.study.domain.Mission; // 경로 확인
import umc.study.domain.Store;  // Store 임포트 추가
import umc.study.domain.User;   // 경로 확인
import umc.study.domain.common.BaseEntity; // 경로 확인
import umc.study.domain.enums.MissionStatus; // 경로 확인

@Entity
// @Table(name = "member_mission") // 실제 테이블 이름이 membermission 이면 제거 또는 수정
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberMissionId; // member_mission_id 가 PK라고 가정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // !!! Store 와의 연관관계 추가 !!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15)")
    private MissionStatus status;

    private Integer number;

    // updated_at 컬럼 이름 DDL 오타 주의 (BaseEntity 에서 updatedAt 으로 처리 가정)
}