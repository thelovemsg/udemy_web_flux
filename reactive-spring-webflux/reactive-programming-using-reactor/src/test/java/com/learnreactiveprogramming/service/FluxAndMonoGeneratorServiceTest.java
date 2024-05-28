package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();

        StepVerifier.create(namesFlux)
//                .expectNext("test1", "test2", "test3")
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void namesFlux_map() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map();

        StepVerifier.create(namesFlux)
                .expectNext("TEST1", "TEST2", "TEST3")
                .verifyComplete();
    }

    @Test
    void namesFlux_immutability() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_immutability();

        StepVerifier.create(namesFlux)
                .expectNext("TEST1", "TEST2", "TEST3")
                .verifyComplete();
    }

    @Test
    void namesFlux_filter() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map(3);

        StepVerifier.create(namesFlux)
                .expectNext("5 - TEST1", "5 - TEST2", "5 - TEST3")
                .verifyComplete();
    }


    @Test
    void namesFlux_flatmap() {
        Flux<String> stringFlux = fluxAndMonoGeneratorService.namesFlux_flatmap(3);
        StepVerifier.create(stringFlux)
                .expectNext("H","E","L","L","O","N","I","C","E","G","O","O","D")
                .verifyComplete();

    }

    @Test
    void namesFlux_flatmap_async() {
        Flux<String> stringFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(3);
        StepVerifier.create(stringFlux)
//                .expectNext("H","E","L","L","O","N","I","C","E","G","O","O","D")
                .expectNextCount(13)
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_concatamp() {
        Flux<String> stringFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_concatamp(3);
        StepVerifier.create(stringFlux)
                .expectNext("H","E","L","L","O","N","I","C","E","G","O","O","D")
//                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void namesMono_flatmap() {
        int stringLength = 3;

        var value = fluxAndMonoGeneratorService.namesMono_flatmap(stringLength);
        StepVerifier.create(value)
                .expectNext(List.of("H","E","L","L","O"))
                .verifyComplete();
    }

    @Test
    void namesMono_flatmapMany() {
        int stringLength = 3;

        var value = fluxAndMonoGeneratorService.namesMono_flatmapMany(stringLength);
        StepVerifier.create(value)
                .expectNext("H","E","L","L","O")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform() {
        int stringLength = 3;

        var value = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);
        StepVerifier.create(value)
                .expectNext("H","E","L","L","O","N","I","C","E","G","O","O","D")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_1() {
        int stringLength = 6;

        var value = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);
        StepVerifier.create(value)
//                .expectNext("H","E","L","L","O","N","I","C","E","G","O","O","D")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_2() {
        int stringLength = 6;

        var value = fluxAndMonoGeneratorService.namesFlux_transform_switchIfEmpty(stringLength);
        StepVerifier.create(value)
                .expectNext("H","E","L","L","O","N","I","C","E","G","O","O","D")
                .verifyComplete();
    }

    @Test
    void exploreConcat() {
        var concatFlux = fluxAndMonoGeneratorService.exploreConcat();
        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C","D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_merge() {
        var value = fluxAndMonoGeneratorService.explore_merge();
        StepVerifier.create(value)
                .expectNext("A", "D", "B","E", "C", "F")
                .verifyComplete();
    }

    @Test
    void explore_zip() {
        var value = fluxAndMonoGeneratorService.explore_zip();
        StepVerifier.create(value)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void explore_zip_1() {
        var value = fluxAndMonoGeneratorService.explore_zip_1();
        StepVerifier.create(value)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }
}