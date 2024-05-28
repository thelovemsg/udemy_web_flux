package com.vinsguru.userservice.controller;

import com.vinsguru.userservice.dto.TransactionRequestDTO;
import com.vinsguru.userservice.dto.TransactionResponseDTO;
import com.vinsguru.userservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user/transaction")
@RequiredArgsConstructor
public class UserTransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDTO> createTransaction(@RequestBody Mono<TransactionRequestDTO> requestDTOMono) {
        return requestDTOMono.flatMap(transactionService::createTransaction);
    }

    @GetMapping("/{userId}")
    public Flux<TransactionResponseDTO> getAllTransactionByUser(@PathVariable int userId) {
        return transactionService.getTransactionsByUser(userId);
    }
}
