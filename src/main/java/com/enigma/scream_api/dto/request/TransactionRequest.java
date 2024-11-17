package com.enigma.scream_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private LocalDateTime transactionDate;

    @NotNull(message = "totalPrice is required")
    private Long totalPrice;

    @NotBlank(message = "playerId is required")
    private String playerId;

    @NotBlank(message = "gameId is required")
    private String gameId;

    @NotBlank(message = "storeId is required")
    private String storeId;

   /* @NotBlank(message = "paymentStatus is required")
    private String paymentStatus;*/
}

