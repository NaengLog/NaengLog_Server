package com.homework.naenglog.global.config;

import com.homework.naenglog.global.filter.JwtFilter;
import com.homework.naenglog.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                // 유저
                .antMatchers(HttpMethod.POST, "/user/login", "/user/register").permitAll()
                // 글쓰기
                .antMatchers(HttpMethod.POST, "/post/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/post/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/post/**").authenticated()
                
                .antMatchers(HttpMethod.GET, "/post/**").permitAll()
                // 댓글
                .antMatchers(HttpMethod.GET, "/comment/**").permitAll()
                .antMatchers(HttpMethod.POST, "/comment/**").authenticated()
                // 이미지 업로드
                .antMatchers(HttpMethod.POST, "/upload/attachments/**").authenticated()
                .antMatchers(HttpMethod.GET, "/upload/attachments/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣는다
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

}
