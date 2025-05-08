package umc.study.apiPayload.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReasonDTO {
    private HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;

    // isSuccess 필드 getter (Lombok @Getter가 생성해주지만 명시)
    public boolean getIsSuccess(){ return isSuccess; }
}