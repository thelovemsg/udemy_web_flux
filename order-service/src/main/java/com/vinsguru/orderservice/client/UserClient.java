package com.vinsguru.orderservice.client;

import com.vinsguru.orderservice.dto.TransactionRequestDTO;
import com.vinsguru.orderservice.dto.TransactionResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {
    private final WebClient webClient;

    public UserClient(@Value("${user.service.url}") String url) {
        webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<TransactionResponseDTO> authorizeTransaction(TransactionRequestDTO requestDTO) {
        return webClient.post()
                .uri("transaction", requestDTO)
                .retrieve()
                .bodyToMono(TransactionResponseDTO.class);
    }
}
