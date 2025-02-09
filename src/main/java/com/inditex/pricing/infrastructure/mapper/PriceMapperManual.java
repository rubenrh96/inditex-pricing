package com.inditex.pricing.infrastructure.mapper;

import org.springframework.stereotype.Component;
import com.inditex.pricing.domain.Price;
import com.inditex.pricing.infrastructure.persistence.PriceEntity;

@Component
public class PriceMapperManual {

    public Price toDomain(PriceEntity entity) {
        if (entity == null) {
            return null;
        }
        Price price = new Price();
        if (entity.getId() != null) {
            price.setBrandId(entity.getId().getBrandId());
            price.setProductId(entity.getId().getProductId());
            price.setPriceList(entity.getId().getPriceList());
        }
        price.setStartDate(entity.getStartDate());
        price.setEndDate(entity.getEndDate());
        price.setPriority(entity.getPriority());
        price.setPrice(entity.getPrice());
        price.setCurrency(entity.getCurrency());
        return price;
    }
}
