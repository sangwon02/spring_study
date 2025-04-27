package umc.study.service.UserService;

import umc.study.web.dto.MyPageInfoDTO; // DTO 임포트

public interface UserQueryService {

    // 사용자 ID로 마이페이지 상단 정보를 조회하는 메서드 시그니처
    MyPageInfoDTO getMyPageInfo(Long userId);
}