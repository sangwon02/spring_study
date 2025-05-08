package umc.study.apiPayload.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReason {

    private HttpStatus httpStatus;
    private final String code;
    private final String message;
    private final boolean isSuccess;

    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(isSuccess)
                .build();
    }

    // BaseErrorCode 인터페이스의 getReasonHttpStatus() 메서드가 반환할 값
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(isSuccess)
                .httpStatus(httpStatus) // HTTP 상태 포함
                .build();
    }
}