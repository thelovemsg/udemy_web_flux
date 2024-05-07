package com.vinsguru.productservice.service;

import com.vinsguru.productservice.dto.ProductDTO;
import com.vinsguru.productservice.repository.ProductRepository;
import com.vinsguru.productservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Flux<ProductDTO> getAll() {
        return repository.findAll().map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDTO> getProductById(String id) {
        return repository.findById(id).map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDTO> insertProduct(Mono<ProductDTO> productDTOMono) {
        return productDTOMono.map(EntityDtoUtil::toEntity)
                .flatMap(repository::insert)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDTO> updateProduct(String id, Mono<ProductDTO> productDTOMono) {
        return repository.findById(id)
                .flatMap(p -> productDTOMono
                                .map(EntityDtoUtil::toEntity)
                                .doOnNext(e -> e.setId(id)))
                .flatMap(repository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return repository.deleteById(id);
    }
}
