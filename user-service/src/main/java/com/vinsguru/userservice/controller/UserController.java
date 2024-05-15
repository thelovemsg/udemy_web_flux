package com.vinsguru.userservice.controller;

import com.vinsguru.userservice.dto.UserDTO;
import com.vinsguru.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public Flux<UserDTO> all() {
        return userService.all();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserDTO>> getUserById(@PathVariable int id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDTO> createUser(@RequestBody Mono<UserDTO> userDTOMono) {
        return userService.createUser(userDTOMono);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDTO>> updateUser(@PathVariable int id, @RequestBody Mono<UserDTO> userDTOMono) {
        return userService.updateUser(id, userDTOMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

}
