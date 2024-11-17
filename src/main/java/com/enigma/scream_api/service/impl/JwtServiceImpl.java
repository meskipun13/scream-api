package com.enigma.scream_api.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.scream_api.constant.Constant;
import com.enigma.scream_api.entity.UserAccount;
import com.enigma.scream_api.service.JwtService;
import com.enigma.scream_api.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final String BLACKLISTED = "BLACKLISTED";
    private final RedisService redisService;

    @Value("${scream.jwt-secret}")
    private String SECRET_KEY;

    @Value("${scream.jwt-expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${scream.jwt-issuer}")
    private String ISSUER;

    @Override
    public String generateAccessToken(UserAccount userAccount) {
        log.info("Generating JWT Token for User : {}", userAccount.getId());
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plus(EXPIRATION_IN_MINUTES, ChronoUnit.MINUTES))
                    .withSubject(userAccount.getId())
                    .withClaim("role", userAccount.getRole().getDescription())
                    .sign(algorithm);

        }catch (JWTCreationException e){
            log.error("JWT Token creation failed: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constant.ERROR_CREATE_JWT);
        }
    }


    @Override
    public String getUserId(String token) {
        DecodedJWT decodedJWT = extractClaimJWT(token);
        if (decodedJWT != null) {
            return decodedJWT.getSubject();
        }
        return null;
    }

    @Override
    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return parseToken(bearerToken);
    }

    @Override
    public void blacklistedAccessToken(String bearerToken) {
        String token = parseToken(bearerToken);
        DecodedJWT decodedJWT = extractClaimJWT(token);

        if (decodedJWT ==null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Token Invalid");
        }

        Date expiredAt = decodedJWT.getExpiresAt();
        long timeLeft = expiredAt.getTime() - System.currentTimeMillis();

        redisService.save(token, BLACKLISTED, Duration.ofMillis(timeLeft));
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        String blacklistToken = redisService.get(token);
        return blacklistToken != null && blacklistToken.equals(BLACKLISTED);
    }

    private DecodedJWT extractClaimJWT(String token) {
        log.info("Extract Token JWT - {}", System.currentTimeMillis());
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            com.auth0.jwt.interfaces.JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception){
            log.error("Error while validate JWT Token: {}", exception.getMessage());
            return null;
        }
    }

    private String parseToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
