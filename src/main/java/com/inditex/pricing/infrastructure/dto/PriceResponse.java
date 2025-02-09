package com.inditex.pricing.infrastructure.dto;

import java.time.LocalDateTime;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceResponse {
    private Integer productId;
    private Integer brandId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private float price;
}
