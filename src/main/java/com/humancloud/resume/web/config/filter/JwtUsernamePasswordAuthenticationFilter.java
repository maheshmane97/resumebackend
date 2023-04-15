package com.humancloud.resume.web.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humancloud.resume.web.dto.LoginRequest;
import com.humancloud.resume.web.entity.Users;
import com.humancloud.resume.web.jwt.JwtConfig;
import com.humancloud.resume.web.jwt.JwtService;
import com.humancloud.resume.web.jwt.util.JwtTokenJsonConverter;
import com.humancloud.resume.web.repository.UserRegistrationRepository;
import com.humancloud.resume.web.service.impl.UserServiceImpl;
import com.humancloud.resume.web.service.security.UserDetailsCustom;
import com.humancloud.resume.web.utils.BaseResponseDTO;
import com.humancloud.resume.web.utils.HelperUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collections;

@Slf4j
public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtService jwtService;

    @Autowired
    private UserRegistrationRepository registrationRepository;

    private  final ObjectMapper objectMapper;
    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager manager,
                                                   JwtConfig jwtConfig,
                                                   JwtService jwtService){
        super(new AntPathRequestMatcher(jwtConfig.getUrl(), "POST"));
        setAuthenticationManager(manager);
        this.objectMapper = new ObjectMapper();
        this.jwtService = jwtService;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("Start attempt to authentication");
        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        log.info("End attempt to authentication");
        return getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword(),
                            Collections.emptyList()));
        }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
            UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authResult.getPrincipal();
            String accessToken = jwtService.generateToken(userDetailsCustom);
            JwtTokenJsonConverter tokenJsonConverter = new JwtTokenJsonConverter();
            tokenJsonConverter.setStatusCode(String.valueOf(HttpStatus.OK.value()));
            tokenJsonConverter.setToken(accessToken);
            tokenJsonConverter.setRole(authResult.getAuthorities().toArray()[0].toString());
            String json = HelperUtils.JSON_WRITER.writeValueAsString(tokenJsonConverter);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset= UTF-8");
            response.getWriter().write(json);
            log.info("End Success authentication: {}",accessToken);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        responseDTO.setMessage(failed.getMessage());

        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
        return;
    }


}
