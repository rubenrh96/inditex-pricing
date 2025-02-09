package com.inditex.pricing.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRICES")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PriceEntity {

    @EmbeddedId
    private PricePK id;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRICE")
    private float price;

    @Column(name = "CURR")
    private String currency;

}
