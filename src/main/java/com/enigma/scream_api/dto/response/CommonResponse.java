package com.enigma.scream_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private Integer status;
    private String message;
    private T data;
    private PagingResponse pagingResponse;
}
