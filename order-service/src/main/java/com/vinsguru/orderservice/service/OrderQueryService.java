package com.vinsguru.orderservice.service;

import com.vinsguru.orderservice.dto.PurchaseOrderResponseDTO;
import com.vinsguru.orderservice.entity.PurchaseOrder;
import com.vinsguru.orderservice.repository.PurchaseOrderRepository;
import com.vinsguru.orderservice.util.EntityDTOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final PurchaseOrderRepository orderRepository;

    public Flux<PurchaseOrderResponseDTO> getProductsByUserId(int userId) {
        /**
         * return Flux.fromIterable(purchaseOrders).map(EntityDTOUtil::getPurchaseOrderResponseDTO);
         *
         * we want things to do done lazily.
         */

        return Flux.fromStream(() -> this.orderRepository.findByUserId(userId).stream()) // blocking
                .map(EntityDTOUtil::getPurchaseOrderResponseDTO)
                .subscribeOn(Schedulers.boundedElastic());
    }

}
