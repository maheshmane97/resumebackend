package com.humancloud.resume.web.jwt.util;

import java.util.HashSet;
import java.util.Set;

public class JwtTokenBlacklist {

    private static final Set<String> BLACKLISTED_TOKENS = new HashSet<>();

    public static void addTokenToBlacklist(String token) {
        BLACKLISTED_TOKENS.add(token);
    }

    public static boolean isTokenBlacklisted(String token) {
        return BLACKLISTED_TOKENS.contains(token);
    }

    public static void removeTokenFromBlacklist(String token) {
        BLACKLISTED_TOKENS.remove(token);
    }
}
