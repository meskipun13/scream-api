package com.enigma.scream_api.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerResponse {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String userId;
}
