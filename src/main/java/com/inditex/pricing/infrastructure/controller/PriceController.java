package com.inditex.pricing.infrastructure.controller;

import com.inditex.pricing.application.PriceService;
import com.inditex.pricing.domain.Price;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/byCompositeId/{brandId}/{productId}/{priceList}")
    public ResponseEntity<Price> getPriceByCompositeId(
            @PathVariable Integer brandId,
            @PathVariable Integer productId,
            @PathVariable Integer priceList) {

        Price price = priceService.getPriceByCompositeId(brandId, productId, priceList);
        return price != null ? ResponseEntity.ok(price) : ResponseEntity.notFound().build();
    }
}

