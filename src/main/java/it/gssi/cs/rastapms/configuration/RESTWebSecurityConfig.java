package it.gssi.cs.rastapms.configuration;

import it.gssi.cs.rastapms.security.CustomAuthenticationFilter;
import it.gssi.cs.rastapms.security.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class RESTWebSecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain restsecurityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.csrf(CsrfConfigurer::disable);
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.securityMatcher("/api/v1/**");
        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/v1/login/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/itineraries/**", "/api/v1/pois/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/v1/itineraries/**", "/api/v1/pois/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/itineraries/**", "/api/v1/pois/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/itineraries/**", "/api/v1/pois/**").permitAll())
                .addFilter(new CustomAuthenticationFilter(authenticationManager))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
