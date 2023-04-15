package com.humancloud.resume.web.config.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.io.IOException;

@Configuration
public class CorsFilterImpl implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        httpServletResponse.setHeader("Access-Control-Allow-Method","*");
        httpServletResponse.setHeader("Access-Control-Allow-Header","Authorization, Content-Type, Accept");
        httpServletResponse.setHeader("Access-Control-Max-Age","3600");

        if(HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod())){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
