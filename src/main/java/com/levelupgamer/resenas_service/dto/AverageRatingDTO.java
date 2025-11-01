package com.levelupgamer.resenas_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AverageRatingDTO {
    private Long productId;
    private Double averageRating;
    private Long totalReviews;
}
