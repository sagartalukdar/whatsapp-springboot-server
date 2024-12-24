package com.react.Config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authorizeHttpRequests(authorize->authorize.requestMatchers("/api/**").authenticated()
		.anyRequest().permitAll())
		.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
		.csrf().disable()
		.cors().configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				
				CorsConfiguration ccf=new CorsConfiguration();
				ccf.setAllowedOrigins(Arrays.asList("http://localhost:3000/","https://whatsapp-react-frontend.vercel.app/"));
				ccf.setAllowedMethods(Collections.singletonList("*"));
				ccf.setAllowCredentials(true);
				ccf.setAllowedHeaders(Collections.singletonList("*"));
				ccf.setExposedHeaders(Arrays.asList("Authorization"));
				ccf.setMaxAge(36000000L);
				
				return ccf;
			}
		}).and().formLogin().and().httpBasic();
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
