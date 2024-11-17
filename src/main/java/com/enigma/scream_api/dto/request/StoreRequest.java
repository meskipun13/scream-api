package com.enigma.scream_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "region is required")
    private String region;

    @NotBlank(message = "currency is required")
    private String currency;

    @NotBlank(message = "developer is required")
    private String developer;
}
