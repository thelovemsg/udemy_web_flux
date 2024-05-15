package com.vinsguru.orderservice.service;

import com.vinsguru.orderservice.client.ProductClient;
import com.vinsguru.orderservice.client.UserClient;
import com.vinsguru.orderservice.dto.PurchaseOrderRequestDTO;
import com.vinsguru.orderservice.dto.PurchaseOrderResponseDTO;
import com.vinsguru.orderservice.dto.RequestContext;
import com.vinsguru.orderservice.util.EntityDTOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {

    private final ProductClient productClient;
    private final UserClient userClient;

    public Mono<PurchaseOrderResponseDTO> processOrder(Mono<PurchaseOrderRequestDTO> requestDTOMono) {
        return null;
//        return requestDTOMono.map(RequestContext::new)
//                .flatMap(this::productRequestResponse)
//                .doOnNext(EntityDTOUtil::setTransactionRequestDTO)
//                .flatMap(this::userRequestResponse)
//                .doOnNext()
    }

    private Mono<RequestContext> productRequestResponse(RequestContext rc) {
        return productClient.getProductById(rc.getPurchaseOrderRequestDTO().getProductId())
                .doOnNext(rc::setProductDTO)
                .thenReturn(rc);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext rc) {
        return userClient.authorizeTransaction(rc.getTransactionRequestDTO())
                .doOnNext(rc::setTransactionResponseDTO)
                .thenReturn(rc);
    }

}
