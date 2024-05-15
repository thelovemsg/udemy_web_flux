package com.vinsguru.userservice.service;

import com.vinsguru.userservice.dto.UserDTO;
import com.vinsguru.userservice.repository.UserRepository;
import com.vinsguru.userservice.util.EntityDTOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public Flux<UserDTO> all() {
        return userRepository.findAll().map(EntityDTOUtil::toDTO);
    }

    public Mono<UserDTO> getUserById(final int userId) {
        return userRepository.findById(userId).map(EntityDTOUtil::toDTO);
    }

    public Mono<UserDTO> createUser(Mono<UserDTO> userDTOMono) {
        return userDTOMono
                .map(EntityDTOUtil::toEntity)
                .flatMap(userRepository::save)
                .map(EntityDTOUtil::toDTO);
    }

    public Mono<UserDTO> updateUser(int id, Mono<UserDTO> userDTOMono) {
        return userRepository.findById(id)
                .flatMap(u -> userDTOMono.map(EntityDTOUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(userRepository::save)
                .map(EntityDTOUtil::toDTO);
    }

    public Mono<Void> deleteUser(int id) {
        return userRepository.deleteById(id);
    }

}
