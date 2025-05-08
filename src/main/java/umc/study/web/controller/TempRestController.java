package umc.study.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.study.apiPayload.ApiResponse;
import umc.study.apiPayload.code.status.SuccessStatus; // SuccessStatus 경로 확인
import umc.study.converter.TempConverter;
import umc.study.service.TempService.TempQueryService; // 서비스 경로 확인
import umc.study.web.dto.TempResponse; // DTO 경로 확인

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TempRestController {

    private final TempQueryService tempQueryService;

    @GetMapping("/test")
    public ApiResponse<TempResponse.TempTestDTO> testAPI(){
        // of 메서드 사용
        return ApiResponse.of(SuccessStatus._OK, TempConverter.toTempTestDTO());
    }

    @GetMapping("/exception")
    public ApiResponse<TempResponse.TempExceptionDTO> exceptionAPI(@RequestParam Integer flag){
        tempQueryService.CheckFlag(flag); // flag 가 1이면 여기서 예외 발생 및 ExceptionAdvice 에서 처리됨

        // flag 가 1이 아니어서 예외가 발생하지 않으면 이 코드가 실행됨
        // of 메서드 사용
        return ApiResponse.of(SuccessStatus._OK, TempConverter.toTempExceptionDTO(flag));
    }
}