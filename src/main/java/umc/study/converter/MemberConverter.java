package umc.study.converter;

import umc.study.domain.User;
import umc.study.domain.enums.Gender;
import umc.study.web.dto.MemberRequestDTO;
import umc.study.web.dto.MemberResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(User user) {
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(user.getUserId()) // User 엔티티의 ID 필드명 확인 (userId)
                .createdAt(user.getCreatedAt()) // BaseEntity의 createdAt 사용
                .build();
    }

    public static User toUser(MemberRequestDTO.JoinDto request) {
        Gender gender = null;
        switch (request.getGender()) {
            case 1: gender = Gender.MALE; break;
            case 2: gender = Gender.FEMALE; break;
            // case 3: gender = Gender.NONE; break; // Gender Enum에 NONE이 없다면 주석 처리 또는 추가
        }

        LocalDate birthDate = LocalDate.of(request.getBirthYear(), request.getBirthMonth(), request.getBirthDay());

        return User.builder()
                .name(request.getName())
                .gender(gender)
                .birthdate(birthDate) // LocalDate 로 변환하여 설정
                .address(request.getAddress())
                .specAddress(request.getSpecAddress())
                .userFavorCategoryList(new ArrayList<>()) // Member 엔티티의 선호 카테고리 리스트 필드명 확인
                // email, socialType 등은 소셜 로그인 연동 시 처리되거나,
                // DTO에 추가 필드를 받아서 설정할 수 있습니다. 여기서는 null로 둡니다.
                .build();
    }
}