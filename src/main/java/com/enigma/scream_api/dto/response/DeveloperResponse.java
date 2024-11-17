package com.enigma.scream_api.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperResponse {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String website;
    private String userId;
}
