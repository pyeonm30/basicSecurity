package io.test.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 시큐리티 필터가 스프링필터체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/user/**").authenticated()  // user는 로그인 한 사람만 들어올수 있다 => 인증만 하면 들어갈 수 있는 주소
				 .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
				 .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll() // 그외 uri는 전부 권한 줌
				.and()
				.formLogin()
				.loginPage("/loginForm")
				.loginProcessingUrl("/login") // 시큐리티가 대신 로그인 프로세스 진행
				.defaultSuccessUrl("/");



	}


}
