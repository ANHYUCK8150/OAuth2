package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	/*
	 * CORS(Cross-Origin Resource Sharing)은 동일한 출처가 아니여도 다른 출처에서의 자원을 요청하여 사용할 수 있도록
	 * 허용하는 구조이다. 오리진은 도메인과 비슷하지만 프로토콜과 포트번호 포함 여부의 차이가 있다.
	 */

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // CORS 적용할 URL 패턴
                .addMapping("/**")
                // 자원을 공유할 오리진 지정
                .allowedOriginPatterns("*")
                // 요청 허용 메서드
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                // 요청 허용 헤더
                .allowedHeaders("*")
                // 쿠키 허용
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}
