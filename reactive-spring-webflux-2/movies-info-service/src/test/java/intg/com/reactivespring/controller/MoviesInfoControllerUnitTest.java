package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MovieInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@WebFluxTest(controllers = MoviesInfoController.class)
@AutoConfigureWebTestClient
public class MoviesInfoControllerUnitTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MovieInfoService moviesInfoServiceMock;

    static String MOVIES_INFO_URL = "/v1/movieinfos";

    @Test
    void getAllMoviesInfo() {
        var movieInfos = List.of(new MovieInfo("abc", "thelovemsg1", 2005, List.of("1,2,3"), LocalDate.parse("2005-01-01"))
                , new MovieInfo(null, "thelovemsg2", 2006, List.of("1,2,3"), LocalDate.parse("2009-01-01"))
                , new MovieInfo(null, "thelovemsg3", 2007, List.of("1,2,3"), LocalDate.parse("2007-01-01"))
        );
        Mockito.when(moviesInfoServiceMock.getAllMovieInfos()).thenReturn(Flux.fromIterable(movieInfos));

        webTestClient
                .get()
                .uri(MOVIES_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);
    }

    @Test
    void addMovieInfo() {
        MovieInfo movieInfo = new MovieInfo(null, "thelovemsg111", -2500, List.of("1,2,3"), LocalDate.parse("2005-01-01"));

        Mockito.when(moviesInfoServiceMock.addMovieInfo(ArgumentMatchers.isA(MovieInfo.class))).thenReturn(
                Mono.just(new MovieInfo("mockId", "thelovemsg111", 2005, List.of("1,2,3"), LocalDate.parse("2005-01-01")))
        );

        webTestClient
                .post()
                .uri(MOVIES_INFO_URL)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    var responseBody = stringEntityExchangeResult.getResponseBody();
                    System.out.println("responseBody : " + responseBody);
                    assert responseBody!=null;
                });
//                .expectBody(MovieInfo.class)
//                .consumeWith(movieInfoEntityExchangeResult -> {
//                    var savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
//                    assert savedMovieInfo!=null;
//                    assert savedMovieInfo.getMovieInfo()!=null;
//                    Assertions.assertEquals("mockId", savedMovieInfo.getMovieInfo());
//                });

    }

    @Test
    void updateMovieInfo() {
        var movieInfoId = "abc";
        MovieInfo movieInfo = new MovieInfo(null, "thelovemsg111", 2005, List.of("1,2,3"), LocalDate.parse("2005-01-01"));

        Mockito.when(moviesInfoServiceMock.updateMovieInfo(ArgumentMatchers.isA(MovieInfo.class),ArgumentMatchers.isA(String.class))).thenReturn(
                Mono.just(new MovieInfo(movieInfoId, "thelovemsg111", 2005, List.of("1,2,3"), LocalDate.parse("2005-01-01")))
        );

        webTestClient
                .put()
                .uri(MOVIES_INFO_URL + "/{id}", movieInfoId)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assert savedMovieInfo!=null;
                    assert savedMovieInfo.getMovieInfo()!=null;
                    Assertions.assertEquals("abc", savedMovieInfo.getMovieInfo());
                });
    }

    @Test
    void updateMovieInfo_notfound() {
        var movieInfoId = "abc";
        var movieInfo = new MovieInfo(null, "thelovemsg111", 2005, List.of("1,2,3"), LocalDate.parse("2005-01-01"));

        webTestClient
                .put()
                .uri(MOVIES_INFO_URL + "/{id}", movieInfoId)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isNotFound();

    }

}
