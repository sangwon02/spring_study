package umc.study.apiPayload.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import umc.study.apiPayload.ApiResponse;
import umc.study.apiPayload.code.ErrorReasonDTO;
import umc.study.apiPayload.code.status.ErrorStatus; // ErrorStatus 임포트

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors; // Collectors 임포트

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    // @Valid 검증 실패 시 (@RequestParam, @PathVariable 에 @Validated 사용 시)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage) // .getMessage() 사용
                .collect(Collectors.joining(", ")); // 여러 에러 메시지 결합

        // _BAD_REQUEST 사용하고, 상세 메시지는 result 에 담음
        return handleExceptionInternalConstraint(e, ErrorStatus._BAD_REQUEST, HttpHeaders.EMPTY, request, errorMessage);
    }

    // @Valid 검증 실패 시 (@RequestBody)
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse(""),
                        (existingErrorMessage, newErrorMessage) -> existingErrorMessage // 첫 번째 에러 메시지만 사용 (필요시 수정)
                ));

        // _BAD_REQUEST 사용하고, 상세 에러 필드 정보는 result 에 담음
        return handleExceptionInternalArgs(e, HttpHeaders.EMPTY, ErrorStatus._BAD_REQUEST, request, errors);
    }

    // 대부분의 기타 예외 처리 (최후의 보루)
    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        // e.printStackTrace() 대신 로거 사용
        log.error("Unhandled exception occurred: {}", e.getMessage(), e);

        // _INTERNAL_SERVER_ERROR 사용하고, 간단한 에러 메시지만 result 에 담음
        return handleExceptionInternalFalse(e, ErrorStatus._INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY, ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus(), request, e.getMessage());
    }

    // 직접 정의한 GeneralException (및 하위 클래스) 처리
    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity<Object> onThrowException(GeneralException generalException, HttpServletRequest request) {
        ErrorReasonDTO errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();
        // GeneralException 에서 추출한 ErrorReasonDTO 를 기반으로 응답 생성
        return handleExceptionInternal(generalException, errorReasonHttpStatus, null, request);
    }

    // GeneralException 처리용 (ErrorReasonDTO 사용)
    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDTO reason,
                                                           HttpHeaders headers, HttpServletRequest request) {
        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(), reason.getMessage(), null); // data 는 null
        WebRequest webRequest = new ServletWebRequest(request);
        return super.handleExceptionInternal(e, body, headers, reason.getHttpStatus(), webRequest);
    }

    // 기타 Exception 처리용 (간단한 메시지 data 로 전달)
    private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, ErrorStatus errorCommonStatus,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), errorPoint);
        return super.handleExceptionInternal(e, body, headers, status, request);
    }

    // @Valid 관련 에러 처리용 (Map<String, String> data 로 전달)
    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers, ErrorStatus errorCommonStatus,
                                                               WebRequest request, Map<String, String> errorArgs) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), errorArgs);
        return super.handleExceptionInternal(e, body, headers, errorCommonStatus.getHttpStatus(), request);
    }

    // ConstraintViolationException 처리용 (String data 로 전달)
    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorStatus errorCommonStatus,
                                                                     HttpHeaders headers, WebRequest request, String errorMsg) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), errorMsg);
        return super.handleExceptionInternal(e, body, headers, errorCommonStatus.getHttpStatus(), request);
    }
}