package com.enigma.scream_api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private LocalDateTime transactionDate;
    private Long totalPrice;
    private String playerId;
    private String gameId;
    private String storeId;
    private String paymentStatus;
}

