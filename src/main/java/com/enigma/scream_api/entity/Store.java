package com.enigma.scream_api.entity;

import com.enigma.scream_api.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = Constant.STORE_TABLE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name_store",nullable = false)
    private String name;

    @Column(name = "region",nullable = false)
    private String region;

    @Column(name = "currency",nullable = false)
    private String currency;

    @OneToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

//    @OneToMany(mappedBy = "platform", orphanRemoval = true)
//    private List<StoreImage> images;
}
