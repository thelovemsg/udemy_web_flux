package com.vinsguru.orderservice.util;

import com.vinsguru.orderservice.dto.*;
import com.vinsguru.orderservice.entity.PurchaseOrder;
import org.springframework.beans.BeanUtils;

public class EntityDTOUtil {

    public static void setTransactionRequestDTO(RequestContext rc) {
        TransactionRequestDTO dto = new TransactionRequestDTO();
        dto.setUserId(rc.getPurchaseOrderRequestDTO().getUserId());
        dto.setAmount(rc.getProductDTO().getPrice());
        rc.setTransactionRequestDTO(dto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDTO().getUserId());
        purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDTO().getProductId());
        purchaseOrder.setAmount(requestContext.getProductDTO().getPrice());

        TransactionStatus status = requestContext.getTransactionResponseDTO().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
        purchaseOrder.setStatus(orderStatus);
        return purchaseOrder;
    }

    public static PurchaseOrderResponseDTO getPurchaseOrderResponseDTO(PurchaseOrder purchaseOrder) {
        PurchaseOrderResponseDTO dto = new PurchaseOrderResponseDTO();
        BeanUtils.copyProperties(purchaseOrder, dto);
        dto.setOrderId(purchaseOrder.getId());
        return dto;
    }
}
