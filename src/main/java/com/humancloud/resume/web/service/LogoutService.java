package com.humancloud.resume.web.service;

import com.humancloud.resume.web.jwt.JwtConfig;
import com.humancloud.resume.web.jwt.util.JwtTokenBlacklist;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    @Autowired
    private JwtConfig jwtConfig;
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {

        String accessToken = request.getHeader(jwtConfig.getHeader());
        if(!ObjectUtils.isEmpty(accessToken) && accessToken.startsWith(jwtConfig.getPrefix()+ " ")) {
            JwtTokenBlacklist.addTokenToBlacklist(accessToken);
            SecurityContextHolder.clearContext();

        }
    }
}
