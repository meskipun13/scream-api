package com.enigma.scream_api.repository;

import com.enigma.scream_api.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {

    @Query(value = "SELECT * FROM m_user_account WHERE username = :username", nativeQuery = true)
    Optional<UserAccount> findByUsername(@Param("username") String username);

    @Query(value = "SELECT COUNT(*) > 0 FROM m_user_account WHERE username = :username", nativeQuery = true)
    boolean existsByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM m_user_account WHERE id = :id", nativeQuery = true)
    Optional<UserAccount> findById(@Param("id") String id);

    @Modifying
    @Query(value = "INSERT INTO m_user_account (id, username, password, role) VALUES (:id, :username, :password, :role)", nativeQuery = true)
    void saveUserAccount(
            @Param("id") String id,
            @Param("username") String username,
            @Param("password") String password,
            @Param("role") String role
    );

    @Modifying
    @Query(value = "UPDATE m_user_account SET password = :password WHERE id = :id", nativeQuery = true)
    void updatePassword(@Param("id") String id, @Param("password") String password);
}

