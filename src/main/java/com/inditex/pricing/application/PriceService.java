package com.inditex.pricing.application;

import com.inditex.pricing.domain.Price;
import com.inditex.pricing.infrastructure.mapper.PriceMapperManual;
import com.inditex.pricing.infrastructure.persistence.PriceEntity;
import com.inditex.pricing.infrastructure.persistence.PricePK;
import com.inditex.pricing.infrastructure.repository.PriceJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class PriceService {

    private final PriceJpaRepository repository;
    private final PriceMapperManual priceMapper;

    public PriceService(PriceJpaRepository repository, PriceMapperManual priceMapper) {
        this.repository = repository;
        this.priceMapper = priceMapper;
    }

    public Price getApplicablePrice(Integer productId, Integer brandId, LocalDateTime applicationDate) {
        var prices = repository.findApplicablePrices(productId, brandId, applicationDate);
        var applicableEntity = prices.stream()
                .max(Comparator.comparingInt(PriceEntity::getPriority));
        return applicableEntity.map(priceMapper::toDomain).orElse(null);
    }

    public Price getPriceByCompositeId(Integer brandId, Integer productId, Integer priceList) {
        var pk = new PricePK(brandId, productId, priceList);
        var optionalEntity = repository.findById(pk);
        return optionalEntity.map(priceMapper::toDomain).orElse(null);
    }


}


