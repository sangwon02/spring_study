package umc.study.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import umc.study.domain.Store;
import umc.study.domain.User;
import umc.study.domain.common.BaseEntity; // 리뷰는 보통 생성시각 등이 필요

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity { // 생성/수정 시각 관리 위해 BaseEntity 상속 권장

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // FK 제약조건 기준 nullable=false 추가
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false) // FK 제약조건 기준 nullable=false 추가
    private Store store;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String body;

    private Float score;

    // 연관관계 (ReviewImg)
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImg> reviewImgList = new ArrayList<>();
}