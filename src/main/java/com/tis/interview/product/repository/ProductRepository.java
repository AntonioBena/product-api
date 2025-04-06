package com.tis.interview.product.repository;

import com.tis.interview.product.model.PopularProduct;
import com.tis.interview.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);
    Page<Product> findAllBy(Pageable pageable);

    @Query(value = """
    SELECT p FROM Product p
    WHERE (:productName IS NOT NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :productName, '%')))
      AND (:productCode IS NOT NULL OR LOWER(p.code) LIKE LOWER(CONCAT('%', :productCode, '%')))
""")
    Page<Product> findAllByFilters(@Param("productName") String productName,
                                   @Param("productCode") String productCode,
                                   Pageable pageable);

    @Query(value = """
                SELECT p.name AS productName, COALESCE(AVG(r.rating), 0) AS productAverageRating
                FROM product p
                LEFT JOIN review r ON p.id = r.product_id
                GROUP BY p.id, p.name
                ORDER BY productAverageRating DESC
            """, nativeQuery = true)
    Page<PopularProduct> findTopPopularProducts(Pageable pageable);
}