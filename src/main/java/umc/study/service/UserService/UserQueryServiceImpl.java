package umc.study.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.repository.UserRepository.UserRepository;
import umc.study.web.dto.MyPageInfoDTO;

@Service
@RequiredArgsConstructor // 생성자 주입 (Lombok)
@Transactional(readOnly = true) // 조회 메서드이므로 readOnly 적용
public class UserQueryServiceImpl implements UserQueryService { // 인터페이스 구현 명시

    private final UserRepository userRepository; // Repository 주입

    @Override // 인터페이스 메서드 구현 명시
    public MyPageInfoDTO getMyPageInfo(Long userId) {
        // Repository의 커스텀 메서드를 호출하고, 결과가 없으면 예외 발생 (예시)
        return userRepository.findMyPageInfoById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. ID: " + userId));
    }
}