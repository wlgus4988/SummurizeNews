package kr.inhatc.spring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@AllArgsConstructor
@Log
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	protected void configure(HttpSecurity security) throws Exception {

		// 인증되지 않은 사용자 접근 경로
		security.authorizeRequests().antMatchers("/","/login/**").permitAll();

		// MEMBER 권한 사용자 접근 경로
		security.authorizeRequests().antMatchers("/login/loginSuccess","/home/user","/user/**","/board/**","/test/**").hasAnyRole("MEMBER","ADMIN");

		// ADMIN 권한 사용자 접근 경로
		security.authorizeRequests().antMatchers("/home/admin","/Admin/**").hasRole("ADMIN");

		// RESTfull을 사용하기 위해서는 비활성화
		security.csrf().disable();

		// 로그인 관련 페이지와 성공시 이동할 페이지
//		security.formLogin().loginPage("/login/login").defaultSuccessUrl("/home", true);
		security.formLogin().loginPage("/").defaultSuccessUrl("/login/loginSuccess", true);

		// 로그인 실패시 이동할 페이지
		security.exceptionHandling().accessDeniedPage("/login/accesDenied");

		// 로그아웃 요청시 세션을 강제 종료하고 시작 페이지로 이동
		security.logout().logoutUrl("/login/logout").invalidateHttpSession(true).logoutSuccessUrl("/");

	}

	/*
	 * <pre> 
	 * 1. 개요 : 패스워드에 대한 암호화 처리 
	 * 2. 처리 내용 : 암호화 처리 
	 * </pre>
	 * 
	 * @Method Name : passwordEncoder
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}