package umc.study.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

public class MissionResponseDTO {

    @Builder @Getter @NoArgsConstructor @AllArgsConstructor
    public static class AddMissionResultDTO {
        private Long missionId;
        private Long storeId;
        private LocalDateTime createdAt;
    }

    // --- 가게 미션 목록 조회용 DTO (추가) ---
    @Builder @Getter @NoArgsConstructor @AllArgsConstructor
    public static class MissionPreviewDTO {
        private Long missionId;
        private String missionSpec;
        private Integer point;
        private LocalDateTime deadline;
    }

    @Builder @Getter @NoArgsConstructor @AllArgsConstructor
    public static class MissionPreviewListDTO {
        List<MissionPreviewDTO> missionList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }
}