package umc.study.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI UMCStudyAPI() {
        Info info = new Info()
                .title("UMC Study API")
                .description("UMC 8기 Spring Study API 명세서입니다.") // 버전 정보 업데이트 (예시)
                .version("1.0.0");

        // JWT 관련 SecurityRequirement 및 Components 설정 부분 제거
        // String jwtSchemeName = "JWT 토큰";
        // SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // Components components = new Components()
        //         .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
        //                 .name(jwtSchemeName)
        //                 .type(SecurityScheme.Type.HTTP)
        //                 .scheme("bearer")
        //                 .bearerFormat("JWT"));

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info);
        // .addSecurityItem(securityRequirement) // JWT 관련 부분 제거
        // .components(components); // JWT 관련 부분 제거
    }
}