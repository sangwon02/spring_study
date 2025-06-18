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
        LocalDate birthDate = LocalDate.of(request.getBirthYear(), request.getBirthMonth(), request.getBirthDay());

        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .gender(gender)
                .birthdate(birthDate)
                .address(request.getAddress())
                .specAddress(request.getSpecAddress())
                .role(request.getRole())
                .userFavorCategoryList(new ArrayList<>())
                .build();
    }

    public static MemberResponseDTO.LoginResultDTO toLoginResultDTO(Long userId, String accessToken) {
        return MemberResponseDTO.LoginResultDTO.builder()
                .memberId(userId)
                .accessToken(accessToken)
                .build();
    }

    public static MemberResponseDTO.MemberInfoDTO toMemberInfoDTO(User user) {
        String genderString = "";
        if (user.getGender() != null) {
            genderString = user.getGender().toString();
        }

        return MemberResponseDTO.MemberInfoDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .gender(genderString)
                .build();
    }
}