package umc.study.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import umc.study.web.dto.MemberResponseDTO;
import umc.study.web.dto.MyPageInfoDTO;

public interface UserQueryService {
    MyPageInfoDTO getMyPageInfo(Long userId);
    MemberResponseDTO.MemberInfoDTO getMemberInfo(HttpServletRequest request);
}