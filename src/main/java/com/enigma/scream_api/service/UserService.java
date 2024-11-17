package com.enigma.scream_api.service;

import com.enigma.scream_api.dto.request.UserRequest;
import com.enigma.scream_api.dto.request.UserUpdatePasswordRequest;
import com.enigma.scream_api.dto.response.UserResponse;
import com.enigma.scream_api.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserResponse create(UserRequest request);

    UserAccount create(UserAccount userAccount);

    UserAccount getById(String id);
    UserResponse getAuthentication();

    void updatePassword(String id, UserUpdatePasswordRequest request);
}
