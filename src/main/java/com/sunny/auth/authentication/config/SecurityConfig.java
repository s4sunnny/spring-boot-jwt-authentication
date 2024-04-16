package com.sunny.auth.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.OctetSequenceKey;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	@Value("${jwt.secret.key}")
	private String key;
	
	@Value("${jwt.secret.algorithm}")
	private String algorithm;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
		.oauth2ResourceServer(config -> config.jwt(Customizer.withDefaults()));
		http.authorizeHttpRequests(
				(requests) -> requests
					.requestMatchers(
							"/",
							"/user/userLogin",
							"/swagger-ui/*",
							"/swagger-ui/index.html",
							"/swagger-resources/*",
							"/webjars/swagger-ui/*",
							"/webjars/**",
							"/configuration/ui",
							"/v3/api-docs/*",
							"/swagger-ui.html",
							"/api/auth/**",
							"/configuration/**"
							)
					.permitAll()
					.anyRequest()
					.authenticated())
				.formLogin((form) -> form.loginPage("/login")
						.permitAll())
				.logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true"));

		return http.build();
	}
	
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().requestMatchers("/user/userLogin").requestMatchers("/user/getUserIP").requestMatchers("/user/resetPassword")
//		.requestMatchers("/user/changePassword").requestMatchers("/swagger-ui/*").requestMatchers("/swagger-ui/index.html")
//		.requestMatchers("/swagger-resources/*").requestMatchers("/v3/api-docs/*").requestMatchers("/swagger-ui.html")
//		.requestMatchers("/webjars/swagger-ui/*").requestMatchers("/configuration/ui").requestMatchers("/configuration/**")
//		.requestMatchers("/webjars/swagger-ui/*").requestMatchers("/webjars/**").requestMatchers("/api/auth/**");
//	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withSecretKey(new OctetSequenceKey.Builder(key.getBytes())
				.algorithm(JWSAlgorithm.parse(algorithm)).build().toSecretKey()).build();
	}

}
