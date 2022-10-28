package gukjin.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter cosFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답 시 자격인증을 받아들일 지 설정
        config.addAllowedOrigin("http://localhost:3000/"); // 리액트 서버만 허용
        config.addAllowedMethod("*"); // 모든 메서드 허용
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config); // 전체에 cors 필터 설정
        return new CorsFilter(source);
    }
}
