package com.humancloud.resume.web.jwt.util;

import lombok.Data;

@Data
public class JwtTokenJsonConverter {

    private String statusCode;
    private String token;
    private String role;
}
