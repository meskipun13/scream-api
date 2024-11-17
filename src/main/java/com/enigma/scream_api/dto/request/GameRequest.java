package com.enigma.scream_api.dto.request;

import com.enigma.scream_api.entity.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message = "price is required")
    private Long price;

    @NotBlank(message = "platform is required")
    private String platform;

    @NotBlank(message = "category is required")
    private String category;

    @NotNull(message = "stock is required")
    private Integer stock;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "store is required")
    private String store;
}
