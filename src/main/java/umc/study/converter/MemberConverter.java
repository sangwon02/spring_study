package umc.study.converter;

import umc.study.domain.User;
import umc.study.domain.enums.Gender;
import umc.study.web.dto.MemberRequestDTO;
import umc.study.web.dto.MemberResponseDTO;
import java.time.LocalDate; // LocalDate import
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(User user) {
        // 이 부분은 기존과 동일
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(user.getUserId())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static User toUser(MemberRequestDTO.JoinDto request) {
        Gender gender = null;
        switch (request.getGender()) {
            case 1: gender = Gender.MALE; break;
            case 2: gender = Gender.FEMALE; break;
        }

        // >> 년, 월, 일을 합쳐 LocalDate 객체를 만드는 로직 추가 <<
        LocalDate birthDate = LocalDate.of(request.getBirthYear(), request.getBirthMonth(), request.getBirthDay());

        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .gender(gender)
                .birthdate(birthDate) // >> 생성된 birthDate를 엔티티에 설정 <<
                .address(request.getAddress())
                .specAddress(request.getSpecAddress())
                .role(request.getRole())
                .userFavorCategoryList(new ArrayList<>())
                .build();
    }
}