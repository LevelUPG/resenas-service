package com.levelupgamer.resenas_service.repository;

import com.levelupgamer.resenas_service.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Query Objetual - Buscar reseñas por producto
    @Query("SELECT r FROM Review r WHERE r.productId = :productId ORDER BY r.createdAt DESC")
    List<Review> findByProductId(@Param("productId") Long productId);

    // Query Nativa - Buscar reseñas por producto
    @Query(value = "SELECT * FROM reviews WHERE product_id = :productId ORDER BY created_at DESC", 
        nativeQuery = true)
    List<Review> findByProductIdNative(@Param("productId") Long productId);

    // Query Objetual - Buscar reseñas por usuario
    @Query("SELECT r FROM Review r WHERE r.userId = :userId ORDER BY r.createdAt DESC")
    List<Review> findByUserId(@Param("userId") Long userId);

    // Query Nativa - Calcular promedio de calificación
    @Query(value = "SELECT AVG(rating) FROM reviews WHERE product_id = :productId", 
        nativeQuery = true)
    Double getAverageRatingByProductId(@Param("productId") Long productId);

    // Query Objetual - Verificar si usuario ya reseñó producto
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Review r " +
        "WHERE r.userId = :userId AND r.productId = :productId")
    boolean existsByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    // Query Objetual - Buscar reseña específica de usuario y producto
    @Query("SELECT r FROM Review r WHERE r.userId = :userId AND r.productId = :productId")
    Optional<Review> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    // Query Nativa - Contar reseñas por producto
    @Query(value = "SELECT COUNT(*) FROM reviews WHERE product_id = :productId", 
        nativeQuery = true)
    long countByProductId(@Param("productId") Long productId);
}
