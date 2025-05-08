package umc.study.apiPayload.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ReasonDTO {
    private HttpStatus httpStatus; // HTTP 상태 코드 필드 추가 (getReasonHttpStatus 용)
    private final boolean isSuccess;
    private final String code;
    private final String message;

    public boolean getIsSuccess(){ return isSuccess; }
}