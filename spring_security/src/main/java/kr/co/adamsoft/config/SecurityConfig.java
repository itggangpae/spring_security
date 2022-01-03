package kr.co.adamsoft.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import kr.co.adamsoft.security.CustomLoginSuccessHandler;
import kr.co.adamsoft.security.CustomUserDetailsService;
import lombok.Setter;

//스프링 Security Config 클래로 만들기 위한 어노테이션
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//DataSource 주입
	@Setter(onMethod_ = { @Autowired })
	private DataSource dataSource;

	//빈 생성
	@Bean
	public UserDetailsService customUserService() {
		return new CustomUserDetailsService();
	}


	//로그인 성공했을 때 호출되는 핸들러 생성
	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}

	//패스워드 인코더 빈 생성
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//데이터저장소 관련 빈 생성
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}

	//CustomUserService 설정
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());
	}


	//설정
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//로그인 페이지 관련 설정
		http.authorizeRequests()
		.antMatchers("/sample/all").permitAll()
		.antMatchers("/sample/admin")
		.access("hasRole('ROLE_ADMIN')")
		.antMatchers("/sample/member")
		.access("hasRole('ROLE_MEMBER')");

		//로그인 페이지 설정
		http.formLogin()
		.loginPage("/customlogin")
		.loginProcessingUrl("/login")
		.successHandler(loginSuccessHandler());

		//로그아웃 설정
		http.logout()
		.logoutUrl("/customlogout")
		.invalidateHttpSession(true)
		.deleteCookies("remember-me","JSESSION_ID");

		//자동 로그인 설정
		http.rememberMe()
		.rememberMeParameter("remember-me")
		.tokenRepository(persistentTokenRepository())
		.tokenValiditySeconds(604800);
	}
}
