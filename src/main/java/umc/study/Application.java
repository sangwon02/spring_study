package umc.study;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import umc.study.service.MissionService.MemberMissionQueryService;
import umc.study.service.UserService.UserQueryService;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.MemberMission;
import umc.study.web.dto.HomeMissionDTO;
import umc.study.web.dto.MyPageInfoDTO;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args); // 클래스 이름 확인
	}

	@Bean
	// UserQueryService 만 주입받도록 수정
	public CommandLineRunner run(UserQueryService userQueryService) {
		return args -> {
			Long myPageTargetUserId = 1L; // 조회할 사용자 ID

			System.out.println("\n=========== 마이페이지 정보 조회 (User ID: " + myPageTargetUserId + ") ===========");
			try {
				MyPageInfoDTO myPageInfo = userQueryService.getMyPageInfo(myPageTargetUserId);

				System.out.println("  - 이름: " + myPageInfo.getName());
				System.out.println("  - 이메일: " + (myPageInfo.getEmail() != null ? myPageInfo.getEmail() : "없음"));
				System.out.println("  - 전화번호: " + (myPageInfo.getPhonenumber() != null ? myPageInfo.getPhonenumber() : "없음"));
				System.out.println("  - 포인트: " + (myPageInfo.getPoint() != null ? myPageInfo.getPoint() : 0));

			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("========================================================\n");

		};
	}
}