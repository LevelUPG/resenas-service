package com.levelupgamer.resenas_service.controller;

import com.levelupgamer.resenas_service.dto.*;
import com.levelupgamer.resenas_service.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody CreateReviewDTO createReviewDTO) {
        log.info("POST /api/reviews/create - Product: {}, User: {}", 
                 createReviewDTO.getProductId(), createReviewDTO.getUserId());

        try {
            ReviewDTO review = reviewService.createReview(createReviewDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(review);
        } catch (IllegalStateException | IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            log.error("Error creating review: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProduct(@PathVariable Long productId) {
        log.info("GET /api/reviews/product/{}", productId);

        try {
            List<ReviewDTO> reviews = reviewService.getReviewsByProduct(productId);
            return ResponseEntity.ok(reviews);
        } catch (RuntimeException e) {
            log.error("Error getting reviews: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@PathVariable Long userId) {
        log.info("GET /api/reviews/user/{}", userId);

        try {
            List<ReviewDTO> reviews = reviewService.getReviewsByUser(userId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            log.error("Error getting user reviews: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody UpdateReviewDTO updateReviewDTO) {

        log.info("PUT /api/reviews/update/{} - User: {}", id, updateReviewDTO.getUserId());

        try {
            ReviewDTO review = reviewService.updateReview(id, updateReviewDTO);
            return ResponseEntity.ok(review);
        } catch (IllegalStateException | IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            log.error("Error updating review: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}/user/{userId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long id,
            @PathVariable Long userId) {

        log.info("DELETE /api/reviews/delete/{} - User: {}", id, userId);

        try {
            reviewService.deleteReview(userId, id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            log.error("Permission error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            log.error("Error deleting review: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/product/{productId}/average")
    public ResponseEntity<AverageRatingDTO> getAverageRating(@PathVariable Long productId) {
        log.info("GET /api/reviews/product/{}/average", productId);

        try {
            AverageRatingDTO average = reviewService.getAverageRating(productId);
            return ResponseEntity.ok(average);
        } catch (RuntimeException e) {
            log.error("Error getting average: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
