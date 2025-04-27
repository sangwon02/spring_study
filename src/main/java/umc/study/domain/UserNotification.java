package umc.study.domain;

import jakarta.persistence.*;
import lombok.*;
// import umc.spring.domain.common.BaseEntity; // 필요시 BaseEntity 상속

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserNotification /* extends BaseEntity */ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // DDL의 user_notifiaction_id 오타 반영 또는 수정된 이름 사용
    private Long userNotificationId; // 필드명은 userNotificationId 로 수정 권장

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // FK 제약조건 기준 nullable=false 추가
    private User user;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "`read`", columnDefinition = "TINYINT(1)") // `read`는 예약어일 수 있으므로 백틱 처리
    private Boolean read; // 필드명 isRead 권장
}