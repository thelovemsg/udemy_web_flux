package com.vinsguru.orderservice.util;

import com.vinsguru.orderservice.dto.RequestContext;
import com.vinsguru.orderservice.dto.TransactionRequestDTO;

public class EntityDTOUtil {

    public static void setTransactionRequestDTO(RequestContext rc) {
        TransactionRequestDTO dto = new TransactionRequestDTO();
        dto.setUserId(rc.getPurchaseOrderRequestDTO().getUserId());
        dto.setAmount(rc.getProductDTO().getPrice());
        rc.setTransactionRequestDTO(dto);
    }
}
