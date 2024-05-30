package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        var movieInfos = List.of(new MovieInfo("abc", "thelovemsg1", 2005, List.of("1,2,3"), LocalDate.parse("2005-01-01"))
                    ,   new MovieInfo(null, "thelovemsg2", 2006, List.of("1,2,3"), LocalDate.parse("2009-01-01"))
                    , new MovieInfo(null, "thelovemsg3", 2007, List.of("1,2,3"), LocalDate.parse("2007-01-01"))
        );

        movieInfoRepository.saveAll(movieInfos)
                .blockLast();

    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void findAll() {
        //given

        //when
        var moviesInfoFlux = movieInfoRepository.findAll().log();

        //then
        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        //given

        //when
        var moviesInfoMono = movieInfoRepository.findById("abc").log();

        //then
        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo -> {
                    Assertions.assertEquals("thelovemsg1", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void saveMovieInfo() {
        //given

        //when
        var moviesInfoMono = movieInfoRepository.save(new MovieInfo("abc", "thelovemsg11", 2005, List.of("1,2,3"), LocalDate.parse("2005-01-01"))).log();

        //then
        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo -> {
                    Assertions.assertEquals("thelovemsg11", movieInfo.getName());
                })
                .verifyComplete();
    }


}