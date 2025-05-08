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

		};
	}
}