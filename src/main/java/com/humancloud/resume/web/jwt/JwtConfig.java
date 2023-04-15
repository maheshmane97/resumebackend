package com.humancloud.resume.web.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtConfig {

    @Value("${jwt.url:/thor/login}")
    private String url;
    @Value("${jwt.header:Authorization}")
    private String header;
    @Value("${jwt.prefix:Bearer}")
    private String prefix;
    @Value("${jwt.expiration:#{60*60}}")
    private int expiration;
    @Value("${jwt.secret:7234743777217A25432A462D4A614E645267556B58703273357638782F413F44}")
    private String secret;

}
