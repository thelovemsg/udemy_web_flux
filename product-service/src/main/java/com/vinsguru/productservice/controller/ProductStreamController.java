package com.vinsguru.productservice.controller;

import com.vinsguru.productservice.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductStreamController {

    private final Flux<ProductDTO> flux;

    @GetMapping(value = "/stream/{maxPrice}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDTO> getProductUpdates(@PathVariable int maxPrice) {
        return flux.filter(dto -> dto.getPrice() <= maxPrice);
    }

}
