package com.levelupgamer.resenas_service.service;

import com.levelupgamer.resenas_service.dto.*;
import com.levelupgamer.resenas_service.entity.Review;
import com.levelupgamer.resenas_service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewDTO createReview(CreateReviewDTO createReviewDTO) {
        log.info("Creating review for product {} by user {}", 
                 createReviewDTO.getProductId(), createReviewDTO.getUserId());

        // Validar que el usuario no haya reseñado ya el producto
        if (reviewRepository.existsByUserIdAndProductId(
                createReviewDTO.getUserId(), createReviewDTO.getProductId())) {
            throw new IllegalStateException("Ya has creado una reseña para este producto");
        }

        // Validar rating entre 1 y 5
        if (createReviewDTO.getRating() < 1 || createReviewDTO.getRating() > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }

        Review review = new Review();
        review.setProductId(createReviewDTO.getProductId());
        review.setUserId(createReviewDTO.getUserId());
        review.setRating(createReviewDTO.getRating());
        review.setComment(createReviewDTO.getComment());

        review = reviewRepository.save(review);
        log.info("Review created with ID: {}", review.getId());

        return convertToDTO(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByProduct(Long productId) {
        log.info("Getting reviews for product {}", productId);
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByUser(Long userId) {
        log.info("Getting reviews by user {}", userId);
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO updateReview(Long reviewId, UpdateReviewDTO updateReviewDTO) {
        log.info("Updating review {}", reviewId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con ID: " + reviewId));

        // Verificar que el usuario es el dueño de la reseña
        if (!review.getUserId().equals(updateReviewDTO.getUserId())) {
            throw new IllegalStateException("No tienes permiso para actualizar esta reseña");
        }

        // Validar rating entre 1 y 5
        if (updateReviewDTO.getRating() < 1 || updateReviewDTO.getRating() > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }

        review.setRating(updateReviewDTO.getRating());
        review.setComment(updateReviewDTO.getComment());

        review = reviewRepository.save(review);
        log.info("Review updated: {}", reviewId);

        return convertToDTO(review);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        log.info("Deleting review {} by user {}", reviewId, userId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con ID: " + reviewId));

        // Verificar que el usuario es el dueño de la reseña
        if (!review.getUserId().equals(userId)) {
            throw new IllegalStateException("No tienes permiso para eliminar esta reseña");
        }

        reviewRepository.delete(review);
        log.info("Review deleted: {}", reviewId);
    }

    @Transactional(readOnly = true)
    public AverageRatingDTO getAverageRating(Long productId) {
        log.info("Calculating average rating for product {}", productId);

        Double average = reviewRepository.getAverageRatingByProductId(productId);
        long count = reviewRepository.countByProductId(productId);

        return AverageRatingDTO.builder()
                .productId(productId)
                .averageRating(average != null ? average : 0.0)
                .totalReviews(count)
                .build();
    }

    private ReviewDTO convertToDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .productId(review.getProductId())
                .userId(review.getUserId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
