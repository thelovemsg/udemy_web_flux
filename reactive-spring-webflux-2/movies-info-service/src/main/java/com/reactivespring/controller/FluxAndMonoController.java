package com.reactivespring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> flux() {
        return Flux.just(1,2,3).log();
    }

    @GetMapping("/mono")
    public Mono<String> mono() {
        return Mono.just("1").log();
    }

    /*
    * Streaming Endpoint
    *
    * Streaming Endpoint is a kind of endpoint which continously sends updates to the clients as the new data arrives
    * This concept is similar to Server Sent Events(SSE)
    * Easy to implement in Spring WebFlux
    *
    * Examples: Stock Tickers, Realtime updates of Sports Events
    * */

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> monoStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .log();
    }

}
