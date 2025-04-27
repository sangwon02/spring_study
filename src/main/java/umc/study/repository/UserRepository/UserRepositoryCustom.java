package umc.study.repository.UserRepository;

import umc.study.web.dto.MyPageInfoDTO; // DTO 임포트

import java.util.Optional;

public interface UserRepositoryCustom {

    // 사용자 ID로 마이페이지 상단 정보 조회
    Optional<MyPageInfoDTO> findMyPageInfoById(Long userId);
}