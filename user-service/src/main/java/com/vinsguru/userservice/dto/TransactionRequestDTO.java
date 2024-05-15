package com.vinsguru.userservice.dto;

import lombok.Data;

@Data
public class TransactionRequestDTO {
    private Integer userId;
    private Integer amount;

}
