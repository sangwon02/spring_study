package umc.study.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class MemberMissionResponseDTO {

    // 단일 미션 정보를 담을 DTO (리스트의 각 항목)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPreviewDTO {
        private Long memberMissionId; // 사용자가 수행중/완료한 미션의 ID
        private String missionSpec;   // 미션 내용
        private Integer point;        // 미션 포인트
        private LocalDateTime deadline;   // 미션 마감 기한
        // 필요하다면 가게 이름 등 추가 정보 포함 가능
        // private String storeName;
    }

    // 미션 목록과 페이징 정보를 함께 담을 DTO (최종 응답 result 필드)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionListDTO {
        private List<MissionPreviewDTO> missionList; // DTO 리스트
        private Integer listSize;        // 현재 페이지의 미션 개수
        private Integer totalPage;       // 전체 페이지 수
        private Long totalElements;     // 전체 미션 개수
        private Boolean isFirst;         // 첫 페이지 여부
        private Boolean isLast;          // 마지막 페이지 여부
    }
}