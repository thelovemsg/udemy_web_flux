package com.vinsguru.orderservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {
    private Integer userId;
    private Integer amount;
    private TransactionStatus status;
    private LocalDateTime transactionDate;
}
