package com.enigma.scream_api.service.impl;

import com.enigma.scream_api.constant.Constant;
import com.enigma.scream_api.constant.UserRole;
import com.enigma.scream_api.dto.request.UserRequest;
import com.enigma.scream_api.dto.request.UserUpdatePasswordRequest;
import com.enigma.scream_api.dto.response.UserResponse;
import com.enigma.scream_api.entity.UserAccount;
import com.enigma.scream_api.repository.UserAccountRepository;
import com.enigma.scream_api.service.UserService;
import com.enigma.scream_api.util.ValidationUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationUtil validationUtil;

    @Value("${scream.user-admin}")
    private String USERNAME_ADMIN;

    @Value("${scream.user-password}")
    private String PASSWORD_ADMIN;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initUser() {
        boolean exist = userAccountRepository.existsByUsername(USERNAME_ADMIN);
        if (exist) return;
        UserAccount userAccount = UserAccount.builder()
                .username(USERNAME_ADMIN)
                .password(passwordEncoder.encode(PASSWORD_ADMIN))
                .role(UserRole.ROLE_ADMIN)
                .build();
        String userId = UUID.randomUUID().toString();
        userAccountRepository.saveUserAccount(
                userId,
                userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.getRole().toString()
        );
        userAccount.setId(userId);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse create(UserRequest request) {
        validationUtil.validate(request);
        try {
            UserRole userRole = UserRole.findByDescription(request.getRole());
            if (userRole == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_ROLE_NOT_FOUND);

            UserAccount userAccount = UserAccount.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(userRole)
                    .build();
            String userId = UUID.randomUUID().toString();
            userAccountRepository.saveUserAccount(
                    userId,
                    userAccount.getUsername(),
                    userAccount.getPassword(),
                    userAccount.getRole().toString()
            );
            userAccount.setId(userId);
            return toResponse(userAccount);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, Constant.ERROR_USERNAME_DUPLICATE);
        }

    }

    @Override
    public UserAccount create(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        String userId = UUID.randomUUID().toString();
        userAccountRepository.saveUserAccount(
                userId,
                userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.getRole().toString()
        );
        userAccount.setId(userId);
        return userAccount;
    }

    @Override
    public UserAccount getById(String id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_USER_NOT_FOUND));
    }

    @Override
    public UserResponse getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        return toResponse(userAccount);
    }

    @Override
    public void updatePassword(String id, UserUpdatePasswordRequest request) {
        validationUtil.validate(request);
        UserAccount userAccount = getById(id);

        if (!passwordEncoder.matches(userAccount.getPassword(), request.getCurrentPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,Constant.INVALID_CREDENTIAL);

        userAccount.setPassword(passwordEncoder.encode(request.getNewPassword()));
        String userId = UUID.randomUUID().toString();
        userAccountRepository.saveUserAccount(
                userId,
                userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.getRole().toString()
        );
        userAccount.setId(userId);

    }

    private UserResponse toResponse(UserAccount userAccount) {
        return UserResponse.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .role(userAccount.getRole().getDescription())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(Constant.ERROR_USERNAME_NOT_FOUND));
    }
}
