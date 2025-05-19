package umc.study.web.dto;

import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import umc.study.validation.annotation.ExistCategories; // 어노테이션 import
import java.util.List;

public class MemberRequestDTO {

    @Getter
    public static class JoinDto {
        @NotBlank
        String name;
        @NotNull
        Integer gender;
        @NotNull
        Integer birthYear;
        @NotNull
        Integer birthMonth;
        @NotNull
        Integer birthDay;
        @NotBlank
        String address;
        @NotBlank
        String specAddress;

        @ExistCategories // 어노테이션 추가
        @Size(min = 1, max = 5)
        List<Long> preferCategory;
    }
}