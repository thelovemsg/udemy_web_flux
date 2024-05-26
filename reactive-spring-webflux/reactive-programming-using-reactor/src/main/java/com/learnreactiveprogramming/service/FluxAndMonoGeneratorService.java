package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("test1", "test2", "test3"))
                .log();
    }

    public Mono<String> nameMono() {
        // we can see what happening
        return Mono.just("alex")
                .log();
    }

    public Flux<String> namesFlux_map() {
        return Flux.fromIterable(List.of("test1", "test2", "test3"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFlux_immutability() {
        var namesFlux = Flux.fromIterable(List.of("test1", "test2", "test3"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    /**
     * filter() operation
     * <p>
     * Userd to filter elements in a Reactive Stream
     * Similar to the filter() operator in Streams API
     *
     * @param args
     */
    public Flux<String> namesFlux_map(int stringLength) {
        return Flux.fromIterable(List.of("test1", "test2", "test3"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length() + " - " + s)
                .log();
    }

    /**
     * flatMap()
     *
     * Transfors one source element to a Flux of 1 to N elements
     * Use it when transformation returns a Reactive Type(Flux or Mono)
     */
    public Flux<String> namesFlux_flatmap(int stringLength) {
        return Flux.fromIterable(List.of("hello", "nice", "good"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitString)
                .log();
    }

    public Flux<String> namesFlux_flatmap_async(int stringLength) {
        return Flux.fromIterable(List.of("hello", "nice", "good"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitString_delay)
                .log();
    }


    /**
     * concatMap()
     *
     * works similar to flatMap()
     *
     * Only difference is that concatMap() preserves the ordering sequence of the Reactive Streams.
     */
    public Flux<String> namesFlux_flatmap_concatamp(int stringLength) {
        return Flux.fromIterable(List.of("hello", "nice", "good"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
//                .flatMap(this::splitString_delay)
                .concatMap(this::splitString)
                .log();
    }

    /**
     * flatMap in Mono
     *
     * Use it when the transformation returns a Mono
     */
    public Mono<List<String>> namesMono_flatmap(int stringLength) {
        return Mono.just("hello")
                .map(String::toUpperCase)
                .filter(s -> s.length()>stringLength)
                .flatMap(this::splitStringMono);
    }

    /**
     * flatMapMany()
     */
    public Flux<String> namesMono_flatmapMany(int stringLength) {
        return Mono.just("hello")
                .map(String::toUpperCase)
                .filter(s -> s.length()>stringLength)
                .flatMapMany(this::splitString);
    }

    /**
     * transform()
     *
     * Used to transform from one type to another
     * Accepts Function Functional Interface
     * Function Functional Interface got released as part of Java 8
     * Input - Publisher (Flux or Mono)
     * Output - Publisher(Flux or Mono)
     */
    public Flux<String> namesFlux_transform(int stringLength) {
        Function<Flux<String>, Flux<String>> filterMap =
                name -> name.map(String::toUpperCase)
                        .filter(s -> s.length() > stringLength);
        return Flux.fromIterable(List.of("hello", "nice", "good"))
                .transform(filterMap)
                .flatMap(this::splitString)
                .log();
    }

    private Mono<List<String>> splitStringMono(String s) {
        var chars = s.split("");
        var charList = List.of(chars);
        return Mono.just(charList);
    }

    //ALEX -> Flux(A,L,E,X)
    public Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> splitString_delay(String name) {
        var charArray = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArray).delayElements(Duration.ofMillis(delay));
    }


    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        // you can access to the data by subscribing it.
        fluxAndMonoGeneratorService.namesFlux().subscribe(name -> System.out.println("Name is : " + name));

        fluxAndMonoGeneratorService.nameMono().subscribe(System.out::println);

    }
}
