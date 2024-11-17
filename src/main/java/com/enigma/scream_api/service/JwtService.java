package com.enigma.scream_api.service;

import com.enigma.scream_api.entity.UserAccount;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
    String generateAccessToken(UserAccount userAccount);

    String getUserId(String token);

    String extractTokenFromRequest(HttpServletRequest request);

    void blacklistedAccessToken(String bearerToken);

    boolean isTokenBlacklisted(String token);
}
