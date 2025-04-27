package umc.study.domain; // 패키지 경로 확인 (umc.study -> umc.spring 가정)

import jakarta.persistence.*;
import lombok.*;
import umc.study.domain.common.BaseEntity; // common 패키지 경로 확인
import umc.study.domain.mapping.Review;
import umc.study.domain.mapping.Review;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId; // 필드명 storeId 유지

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String address;

    private Float score;

    // 연관관계 (Review)
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @Builder.Default // Lombok @Builder 경고 해결을 위해 추가
    private List<Review> reviewList = new ArrayList<>();

    // *** Region, Mission 연관관계는 DB 스키마에 없으므로 추가하지 않음 ***

    // 콘솔 출력용 toString() 메서드 추가 (존재하는 필드만 사용)
    @Override
    public String toString() {
        return "Store{" +
                "storeId=" + storeId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", score=" + score +
                '}';
    }
}