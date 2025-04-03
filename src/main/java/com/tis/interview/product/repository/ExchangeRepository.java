package com.tis.interview.product.repository;

import com.tis.interview.product.model.ExchangeCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<ExchangeCache, Long> {
}
