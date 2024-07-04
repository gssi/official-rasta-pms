package it.gssi.cs.rastapms.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String BAD_CREDENTIAL_MESSAGE = "Authentication failed for username: %s and password: %s";

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        super.setFilterProcessesUrl("/api/v1/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = null;
        String password = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.readValue(request.getInputStream(), Map.class);
            username = map.get("username");
            password = map.get("password");
            //log.debug("Login with username: {}", username);
            return super.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            //log.error(String.format(BAD_CREDENTIAL_MESSAGE, username, password), e);
            throw e;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            Map<String, String> error = new HashMap<>();
            error.put("errorMessage", e.getMessage());
            response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            try {
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(String.format("Error in attemptAuthentication with username %s and password %s", username, password), e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = JwtUtil.createAccessToken(user.getUsername(), request.getRequestURL().toString(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String refreshToken = JwtUtil.createRefreshToken(user.getUsername());
        response.addHeader(CustomSecurityHeaders.ACCESS_TOKEN.getValue(), accessToken);
        response.addHeader(CustomSecurityHeaders.REFRESH_TOKEN.getValue(), refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> error = new HashMap<>();
        error.put("errorMessage", "Bad credentials");
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), error);
    }
}

