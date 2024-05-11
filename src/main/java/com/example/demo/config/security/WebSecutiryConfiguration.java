package com.example.demo.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.config.exception.AuthExceptionHandler;
import com.example.demo.service.AcountService.IAccountService;


@Component
@EnableWebSecurity
public class WebSecutiryConfiguration   {

	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private AuthExceptionHandler authExceptionHandler;

//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(accountService).passwordEncoder(new BCryptPasswordEncoder());
////		auth.user
//		//12346-> $10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi
//	}

//	protected void configure(HttpSecurity http) throws Exception {
//		http
//		.cors()
//		.and()
//		.exceptionHandling()
//        .authenticationEntryPoint(authExceptionHandler)
//        .accessDeniedHandler(authExceptionHandler)
//        .and()
//        .httpBasic()
//        .and()
//		.authorizeRequests()
////			.antMatchers("/api/v1/departments/**").hasAnyAuthority("ADMIN")
////			.antMatchers("/api/v1/accounts/**").hasAnyAuthority("ADMIN")
////			.antMatchers("/api/v1/auth/**").permitAll()
////			.anyRequest().authenticated()
//			.anyRequest().permitAll()
//			.and()
//			.httpBasic()
//			.and()
//			.csrf().disable();
//	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf()
	      .disable()
	      .authorizeHttpRequests()
	      .requestMatchers("/api/v1/accounts/**").hasAnyAuthority("ADMIN")
//	      .requestMatchers("/api/v1/departments/**").hasRole("ROLE_MANAGER")
	      .anyRequest().permitAll()
	      .and()
	      .httpBasic()
	      .and()
	      .sessionManagement()
	      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	    return http.build();
	}
	
//	@Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//		final CorsConfiguration configuration = new CorsConfiguration();
//	    configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
//	    configuration.applyPermitDefaultValues();
//	    
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
