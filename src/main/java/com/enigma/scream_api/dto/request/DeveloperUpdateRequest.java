package com.enigma.scream_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperUpdateRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    @NotBlank(message = "website is required")
    private String website;

}
