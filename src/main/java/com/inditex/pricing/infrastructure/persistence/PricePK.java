package com.inditex.pricing.infrastructure.persistence;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricePK implements Serializable {
    private Integer brandId;
    private Integer productId;
    private Integer priceList;

}