package com.enigma.scream_api.service;

import com.enigma.scream_api.dto.request.AuthRequest;
import com.enigma.scream_api.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login (AuthRequest request);
    AuthResponse refreshToken(String token);
    void logout(String accessToken);
}
