package com.reactivespring.routes;

import com.reactivespring.domain.Review;
import com.reactivespring.exceptionhandler.GlobalErrorHandler;
import com.reactivespring.handler.ReviewHandler;
import com.reactivespring.repository.ReviewReactiveRepository;
import com.reactivespring.router.ReviewRouter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebFluxTest
@ContextConfiguration(classes = {ReviewRouter.class, ReviewHandler.class, GlobalErrorHandler.class})
@AutoConfigureWebTestClient
public class ReviewsUnitTest {

    @MockBean
    private ReviewReactiveRepository reviewReactiveRepository;

    @Autowired
    private WebTestClient webTestClient;

    static String REVIEWS_URL = "/v1/reviews";

    @Test
    void addReview() {

        var review = new Review(null, 1L, "Awesome Movie", 9.0);

        when(reviewReactiveRepository.save(isA(Review.class)))
                .thenReturn(Mono.just(new Review("abc", 1L, "Awesome Movie", 9.0)));

        webTestClient
                .post()
                .uri(REVIEWS_URL)
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Review.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assert savedMovieInfo!=null;
                    assert savedMovieInfo.getReviewId()!=null;
                });

    }

    @Test
    void addReview_validation() {

        var review = new Review(null, null, "Awesome Movie", -9.0);

        when(reviewReactiveRepository.save(isA(Review.class)))
                .thenReturn(Mono.just(new Review("abc", 1L, "Awesome Movie", 9.0)));

        webTestClient
                .post()
                .uri(REVIEWS_URL)
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isBadRequest();
//                .expectBody(Review.class)
//                .consumeWith(movieInfoEntityExchangeResult -> {
//                    var savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
//                    assert savedMovieInfo!=null;
//                    assert savedMovieInfo.getReviewId()!=null;
//                });

    }

    @Test
    void getReviews() {
        //given

        //when
        webTestClient
                .get()
                .uri(REVIEWS_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Review.class)
                .value(reviews -> {
                    assertEquals(3, reviews.size());
                });

    }

    @Test
    void getAllReviews() {
        List<Review> reviews = List.of(
                new Review(null, 1L, "Awesome Movie", 9.0),
                new Review(null, 1L, "Awesome Movie1", 9.0),
                new Review("rev", 3L, "Great Action Movie", 8.5),
                new Review(null, 2L, "Excellent Movie", 8.0));

        when(reviewReactiveRepository.findAll()).thenReturn(Flux.fromIterable(reviews));

        webTestClient
                .get()
                .uri(REVIEWS_URL)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Review.class)
                .hasSize(4);
    }

    @Test
    void updateReview() {
        String id = "rev";

        Review review = new Review("rev", 3L, "Great Action Movie", 8.5);
        Review reviewExisting = new Review("rev", 3L, "Normal Movie", 5.0);

        when(reviewReactiveRepository.findById(isA(String.class))).thenReturn(Mono.just(reviewExisting));
        when(reviewReactiveRepository.save(isA(Review.class))).thenReturn(Mono.just(review));

        webTestClient
                .put()
                .uri(REVIEWS_URL + "/{id}", id)
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Review.class)
                .consumeWith(reviewEntityExchangeResult -> {
                    Review responseBody = reviewEntityExchangeResult.getResponseBody();
                    assert responseBody != null;
                    assertEquals("rev", responseBody.getReviewId());
                    assertEquals("Great Action Movie", responseBody.getComment());
                    assertEquals(8.5, responseBody.getRating());
                    assertEquals(3L, responseBody.getMovieInfoId());
                });
    }

    @Test
    void deleteReview() {
        String id = "rev";
        Review reviewExisting = new Review("rev", 3L, "Normal Movie", 5.0);

        when(reviewReactiveRepository.findById(isA(String.class))).thenReturn(Mono.just(reviewExisting));
        when(reviewReactiveRepository.deleteById(isA(String.class))).thenReturn(Mono.empty());

        webTestClient
                .delete()
                .uri(REVIEWS_URL + "/{id}", id)
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody(Void.class)
                .consumeWith(voidResponse -> {
                    Void responseBody = voidResponse.getResponseBody();
                    assertNull(responseBody);
                });
    }

}
