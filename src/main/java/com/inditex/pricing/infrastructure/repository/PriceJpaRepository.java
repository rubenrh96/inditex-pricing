package com.inditex.pricing.infrastructure.repository;

import com.inditex.pricing.infrastructure.persistence.PriceEntity;
import com.inditex.pricing.infrastructure.persistence.PricePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.*;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, PricePK> {

    @Query("SELECT p FROM PriceEntity p " +
            "WHERE p.id.productId = :productId " +
            "  AND p.id.brandId = :brandId " +
            "  AND p.startDate <= :applicationDate " +
            "  AND p.endDate >= :applicationDate")
    List<PriceEntity> findApplicablePrices(@Param("productId") Integer productId,
                                           @Param("brandId") Integer brandId,
                                           @Param("applicationDate") LocalDateTime applicationDate);

    Optional<PriceEntity> findByIdBrandIdAndIdProductIdAndIdPriceList(
            Integer brandId, Integer productId, Integer priceList);
}
