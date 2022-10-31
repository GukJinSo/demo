package gukjin.demo.config;


import gukjin.demo.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.addFilter(corsFilter); // @CrossOrigin(*)은 인증이 필요하지 않은 요청에만 적용. filter는 모든 요청에 적용

        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").authenticated()
                .anyRequest().permitAll()
            .and().formLogin()
                .loginProcessingUrl("/login-process")
                .defaultSuccessUrl("/", false)

            .and().oauth2Login()
                .defaultSuccessUrl("http://localhost:3000/",true)
                .userInfoEndpoint()
                .userService(principalOauth2UserService); // 구글 로그인 완료 후 처리
        ;
    }


}
