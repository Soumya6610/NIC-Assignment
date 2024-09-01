package com.roleapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.roleapi.authenticate.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtRequestFilter jwtRequestFilter;
	@SuppressWarnings("unused")
	private final UserDetailsService userDetailsService;

	public SecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsService userDetailsService) {
		this.jwtRequestFilter = jwtRequestFilter;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authz -> authz.requestMatchers("/api/auth/login", "api/auth/register")
						.permitAll().requestMatchers("/api/admin/**").hasRole("ADMIN") // Restrict access to ADMIN
						.requestMatchers("/api/od/**").hasRole("OD") // Restrict access to MODERATOR
						.requestMatchers("/api/cd/**").hasRole("CD") // Restrict access to USER
						.anyRequest().authenticated() // All other requests require authentication
				).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true).allowedHeaders("*")
						.allowedMethods("*");
			}
		};
	}
}
