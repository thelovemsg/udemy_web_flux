package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MovieInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
public class MoviesInfoController {

    private final MovieInfoService movieInfoService;

    @PostMapping("/movieinfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo) {
        return movieInfoService.addMovieInfo(movieInfo);
    }

    @GetMapping("/movieinfos")
    public Flux<MovieInfo> getAllMovieInfos(@RequestParam(value = "year", required = false) Integer year) {
        log.info("Year is : {}", year);
        if(year != null) {
            return movieInfoService.getMovieInfoByYear(year);
        }
        return movieInfoService.getAllMovieInfos().log();
    }

//    @GetMapping("/movieinfos/{id}")
//    public Mono<MovieInfo> getAllMovieInfoById(@PathVariable String id) {
//        return movieInfoService.getMovieInfoById(id);
//    }

    @PutMapping("/movieinfos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<MovieInfo>> updateMovieInfo(@RequestBody MovieInfo movieInfo, @PathVariable String id) {

        var updatedMovieInfoMono =  movieInfoService.updateMovieInfo(movieInfo, id);
        return updatedMovieInfoMono
                .map(movieInfo1 -> ResponseEntity.ok()
                        .body(movieInfo1))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @DeleteMapping("/movieinfos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String id) {
        return movieInfoService.deleteMovieInfo(id).log();
    }

    @GetMapping("/movieinfos/{id}")
    public Mono<ResponseEntity<MovieInfo>> getMovieInfoById_approach2(@PathVariable("id") String id) {

        return movieInfoService.getMovieInfoById(id)
                .map(movieInfo1 -> ResponseEntity.ok()
                        .body(movieInfo1))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }
}
