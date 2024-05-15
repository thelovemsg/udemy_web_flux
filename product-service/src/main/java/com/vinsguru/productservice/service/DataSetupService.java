package com.vinsguru.productservice.service;

import com.vinsguru.productservice.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        ProductDTO p1 = new ProductDTO("4k-tv1", 1000);
        ProductDTO p2 = new ProductDTO("4k-tv2", 1100);
        ProductDTO p3 = new ProductDTO("4k-tv3", 1200);
        ProductDTO p4 = new ProductDTO("4k-tv4", 1300);

        Flux.just(p1,p2,p3,p4)
            .flatMap(p -> this.productService.insertProduct(Mono.just(p)))
                .subscribe(System.out::println);
    }
}
