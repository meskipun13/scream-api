package com.enigma.scream_api.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameResponse {
    private String id;
    private String name;
    private Long price;
    private String platform;
    private String category;
    private Integer stock;
    private String description;
    private String id_store;
    private String name_store;
}
