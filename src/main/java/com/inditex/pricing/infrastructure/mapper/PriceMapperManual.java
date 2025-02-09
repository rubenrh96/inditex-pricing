package com.inditex.pricing.infrastructure.mapper;

import com.inditex.pricing.infrastructure.dto.PriceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.inditex.pricing.domain.Price;
import com.inditex.pricing.infrastructure.persistence.PriceEntity;

@Component
public class PriceMapperManual {

    public Price toDomain(PriceEntity entity) {
        if (entity == null) {
            return null;
        }
        var price = new Price();
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

    public PriceResponse priceResponseToPrice(Price price) {
        if (price == null) {
            return null;
        }
        var response = new PriceResponse();
        response.setProductId(price.getProductId());
        response.setBrandId(price.getBrandId());
        response.setPriceList(price.getPriceList());
        response.setStartDate(price.getStartDate());
        response.setEndDate(price.getEndDate());
        response.setPrice(price.getPrice());
        return response;
    }
}
