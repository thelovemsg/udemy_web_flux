package com.reactivespring.handler;

import com.reactivespring.domain.Review;
import com.reactivespring.exception.ReviewDataException;
import com.reactivespring.repository.ReviewReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReviewHandler {

    private final Validator validator;

    private final ReviewReactiveRepository repository;

    public Mono<ServerResponse> addReview(ServerRequest request) {
        return request.bodyToMono(Review.class)
                .doOnNext(this::validate)
                .flatMap(repository::save)
                .flatMap(saveReview -> ServerResponse.status(HttpStatus.CREATED).bodyValue(saveReview));
    }

    private void validate(Review review) {
        var constraintViolations = validator.validate(review);
        log.info("constraintViolations : {}", constraintViolations);
        if(constraintViolations.size() > 0) {
            var errorMessage = constraintViolations
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .sorted()
                                .collect(Collectors.joining(","));
            throw new ReviewDataException(errorMessage);
        }



    }

    public Mono<ServerResponse> getReviews(ServerRequest request) {
        var movieInfoId = request.queryParam("movieInfoId");

        if(movieInfoId.isPresent()) {
            var reviewsFlux = repository.findByMovieInfoId(Long.valueOf(movieInfoId.get()));
            return buildReviewsResponse(reviewsFlux);
        } else {
            var reviewsFlux = repository.findAll();
            return buildReviewsResponse(reviewsFlux);
        }

    }

    private static Mono<ServerResponse> buildReviewsResponse(Flux<Review> reviewsFlux) {
        return ServerResponse.ok().body(reviewsFlux, Review.class);
    }

    public Mono<ServerResponse> updateReview(ServerRequest request) {
        var reviewId = request.pathVariable("id");
        var existingReview = repository.findById(reviewId);
        return existingReview.flatMap(review -> request.bodyToMono(Review.class).map(reqReview -> {
                    review.setComment(reqReview.getComment());
                    review.setRating(reqReview.getRating());
                    return review;
                })
                .flatMap(repository::save)
                .flatMap(savedReview -> ServerResponse.ok().bodyValue(savedReview)));
    }

    public Mono<ServerResponse> deleteReview(ServerRequest request) {
        var reviewId = request.pathVariable("id");
        var existingReview = repository.findById(reviewId);
        return existingReview.flatMap(review -> repository.deleteById(reviewId)
                .then(ServerResponse.noContent().build()));
    }
}
