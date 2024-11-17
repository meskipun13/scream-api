package com.enigma.scream_api.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingResponse {
    private long totalItems;
    private Integer totalPages;
    private Integer page;
    private Integer size;
}
