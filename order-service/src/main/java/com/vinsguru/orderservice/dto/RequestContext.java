package com.vinsguru.orderservice.dto;

import lombok.Data;

@Data
public class RequestContext {

    private PurchaseOrderRequestDTO purchaseOrderRequestDTO;
    private ProductDTO productDTO;
    private TransactionRequestDTO transactionRequestDTO;
    private TransactionResponseDTO transactionResponseDTO;

    public RequestContext(PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        this.purchaseOrderRequestDTO = purchaseOrderRequestDTO;
    }

}
