package com.vinsguru.userservice.service;

import com.vinsguru.userservice.dto.TransactionRequestDTO;
import com.vinsguru.userservice.dto.TransactionResponseDTO;
import com.vinsguru.userservice.dto.TransactionStatus;
import com.vinsguru.userservice.repository.UserRepository;
import com.vinsguru.userservice.repository.UserTransactionRepository;
import com.vinsguru.userservice.util.EntityDTOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final UserTransactionRepository userTransactionRepository;

    public Mono<TransactionResponseDTO> createTransaction(final TransactionRequestDTO requestDTO) {
        return userRepository.updateUserBalance(requestDTO.getUserId(), requestDTO.getAmount()).filter(Boolean::booleanValue)
                .map(b -> EntityDTOUtil.toEntity(requestDTO))
                .flatMap(userTransactionRepository::save)
                .map(ut -> EntityDTOUtil.toDTO(requestDTO, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDTOUtil.toDTO(requestDTO, TransactionStatus.DECLINED));
    }

    public Flux<TransactionResponseDTO> getTransactionsByUser(int userId) {
        return userTransactionRepository.findByUserId(userId).map(EntityDTOUtil::toDTO);
    }
}
