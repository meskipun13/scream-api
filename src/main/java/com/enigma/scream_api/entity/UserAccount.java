package com.enigma.scream_api.entity;

import com.enigma.scream_api.constant.Constant;
import com.enigma.scream_api.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = Constant.USER_ACCOUNT_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", unique = true, length = 30)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> myRoles = List.of(role);
        return myRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.name())).toList();
    }

}
