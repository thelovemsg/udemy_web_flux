package com.vinsguru.orderservice.dto;

import lombok.Data;

@Data
public class PurchaseOrderResponseDTO {
    private Integer orderId;
    private Integer userId;
    private String productId;
    private Integer amount;
    private OrderStatus status;
}
