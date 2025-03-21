package it.gssi.cs.rastapms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Bean
	@Order(2)
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		http.csrf(CsrfConfigurer::disable);
		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/", "/index", "/loginform").permitAll()
				.requestMatchers("/i18n/**", "/dist/**", "/plugins/**", "/favicon.ico", "/maplibre/**", "/images/**", "/itineraries/**", "/pois/**", "/v3/**", "/swagger-ui/**").permitAll()
				.requestMatchers("/backoffice/common/**").authenticated()
				.requestMatchers("/backoffice/poi/**", "/backoffice/image/**", "/backoffice/itinerary/**", "/backoffice/user/**", "/backoffice/sensor/**", "/backoffice/sensorparameter/**").hasAnyRole("administrator"))
				.formLogin((form) -> form.loginPage("/loginform").loginProcessingUrl("/login")
					.failureUrl("/loginform?error=invalidlogin").defaultSuccessUrl("/backoffice/common/welcome", false))
					.logout((logout) -> logout.logoutUrl("/backoffice/logout"))
					.logout((logout) -> logout.logoutSuccessUrl("/"));

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
