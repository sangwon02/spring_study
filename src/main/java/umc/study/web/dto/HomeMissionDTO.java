package umc.study.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeMissionDTO {
    private Long memberMissionId;     // MemberMission ID
    private String missionSpec;       // 미션 내용 (Mission)
    private Integer completionPoint;  // 완료 포인트 (Mission)
    private LocalDateTime deadline;     // 마감 기한 (Mission)
    private String storeName;         // 가게 이름 (Store)
}