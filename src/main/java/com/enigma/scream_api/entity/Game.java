package com.enigma.scream_api.entity;

import com.enigma.scream_api.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = Constant.GAME_TABLE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name_game",nullable = false)
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "bigint check (price>0) ")
    private Long price;

    @Column(name = "platform",nullable = false)
    private String platform;

    @Column(name = "category",nullable = false)
    private String category;

    @Column(name = "stock", nullable = false, columnDefinition = "int check (stock>0) ")
    private Integer stock;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

//    @OneToMany(mappedBy = "product", orphanRemoval = true)
//    private List<ProductImage> images;

}
