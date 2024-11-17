package com.enigma.scream_api.dto.response;

import com.enigma.scream_api.entity.Developer;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponse {
    private String id;
    private String name;
    private String region;
    private String currency;
    private String developer;
}
