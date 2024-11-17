package com.enigma.scream_api.entity;

import com.enigma.scream_api.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = Constant.DEVELOPER_TABLE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "website", nullable = false)
    private String website;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

}
