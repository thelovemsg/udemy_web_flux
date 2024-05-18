package com.vinsguru.orderservice.controller;

import com.vinsguru.orderservice.dto.PurchaseOrderRequestDTO;
import com.vinsguru.orderservice.dto.PurchaseOrderResponseDTO;
import com.vinsguru.orderservice.service.OrderFulfillmentService;
import com.vinsguru.orderservice.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderQueryService orderQueryService;

    @PostMapping
    public Mono<PurchaseOrderResponseDTO> order(@RequestBody Mono<PurchaseOrderRequestDTO> requestDTOMono) {
        return orderFulfillmentService.processOrder(requestDTOMono);
    }

    @GetMapping("user/{id}")
    public Flux<PurchaseOrderResponseDTO> getOrdersByUserID(@PathVariable int userId) {
        return orderQueryService.getProductsByUserId(userId);
    }
}
