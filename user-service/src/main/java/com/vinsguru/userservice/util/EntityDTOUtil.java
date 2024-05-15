package com.vinsguru.userservice.util;

import com.vinsguru.userservice.dto.TransactionRequestDTO;
import com.vinsguru.userservice.dto.TransactionResponseDTO;
import com.vinsguru.userservice.dto.TransactionStatus;
import com.vinsguru.userservice.dto.UserDTO;
import com.vinsguru.userservice.entity.User;
import com.vinsguru.userservice.entity.UserTransaction;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDTOUtil {

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static UserTransaction toEntity(TransactionRequestDTO requestDTO) {
        UserTransaction ut = new UserTransaction();
        ut.setUserId(requestDTO.getUserId());
        ut.setAmount(requestDTO.getAmount());
        ut.setTransactionDate(LocalDateTime.now());
        return ut;
    }

    public static TransactionResponseDTO toDTO(TransactionRequestDTO requestDTO, TransactionStatus status) {
        TransactionResponseDTO responseDTO = new TransactionResponseDTO();
        responseDTO.setAmount(responseDTO.getAmount());
        responseDTO.setUserId(responseDTO.getUserId());
        responseDTO.setStatus(status);
        return responseDTO;
    }

    public static TransactionResponseDTO toDTO(UserTransaction userTransaction) {
        TransactionResponseDTO responseDTO = new TransactionResponseDTO();
        responseDTO.setUserId(userTransaction.getUserId());
        responseDTO.setAmount(userTransaction.getAmount());
        responseDTO.setTransactionDate(userTransaction.getTransactionDate());
        return responseDTO;
    }
}
