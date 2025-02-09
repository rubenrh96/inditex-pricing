package com.inditex.pricing.infrastructure.mapper;

import com.inditex.pricing.infrastructure.dto.PriceResponse;
import org.springframework.stereotype.Component;
import com.inditex.pricing.domain.Price;
import com.inditex.pricing.infrastructure.persistence.PriceEntity;

@Component
public class PriceMapperManual {

    public Price toDomain(PriceEntity entity) {
        if (entity == null) {
            return null;
        }
        return Price.builder()
                .brandId(entity.getId() != null ? entity.getId().getBrandId() : null)
                .productId(entity.getId() != null ? entity.getId().getProductId() : null)
                .priceList(entity.getId() != null ? entity.getId().getPriceList() : null)
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priority(entity.getPriority())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .build();
    }

    public PriceResponse priceResponseToPrice(Price price) {
        if (price == null) {
            return null;
        }
        return PriceResponse.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .build();
    }

}
