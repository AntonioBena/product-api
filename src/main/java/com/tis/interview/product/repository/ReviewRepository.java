package com.tis.interview.product.repository;

import com.tis.interview.product.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE (:id IS NOT NULL AND r.id = :id)")
    Optional<Review> findByIdOrReturnNull(@Param("id") Long id);
    Page<Review> findAllByProductCode(String productCode, Pageable pageable);
}