package com.inditex.pricing.application;

import com.inditex.pricing.domain.Price;
import com.inditex.pricing.infrastructure.mapper.PriceMapperManual;
import com.inditex.pricing.infrastructure.persistence.PriceEntity;
import com.inditex.pricing.infrastructure.persistence.PricePK;
import com.inditex.pricing.infrastructure.repository.PriceJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PriceService {

    private final PriceJpaRepository repository;
    private final PriceMapperManual priceMapper;

    public PriceService(PriceJpaRepository repository, PriceMapperManual priceMapper) {
        this.repository = repository;
        this.priceMapper = priceMapper;
    }

    public Price getPriceByCompositeId(Integer brandId, Integer productId, Integer priceList) {
        PricePK pk = new PricePK(brandId, productId, priceList);
        Optional<PriceEntity> optionalEntity = repository.findById(pk);
        return optionalEntity.map(priceMapper::toDomain).orElse(null);
    }


}


