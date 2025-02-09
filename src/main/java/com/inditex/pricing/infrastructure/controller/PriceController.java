package com.inditex.pricing.infrastructure.controller;

import com.inditex.pricing.application.PriceService;
import com.inditex.pricing.domain.Price;
import com.inditex.pricing.infrastructure.dto.PriceResponse;
import com.inditex.pricing.infrastructure.mapper.PriceMapperManual;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private final PriceService priceService;
    private final PriceMapperManual priceMapper;

    public PriceController(PriceService priceService, PriceMapperManual priceMapper) {
        this.priceService = priceService;
        this.priceMapper = priceMapper;
    }

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam Integer productId,
            @RequestParam Integer brandId) {

        var price = priceService.getApplicablePrice(productId, brandId, applicationDate);
        if (price != null) {
            return ResponseEntity.ok(priceMapper.priceResponseToPrice(price));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byCompositeId/{brandId}/{productId}/{priceList}")
    public ResponseEntity<Price> getPriceByCompositeId(
            @PathVariable Integer brandId,
            @PathVariable Integer productId,
            @PathVariable Integer priceList) {

        var price = priceService.getPriceByCompositeId(brandId, productId, priceList);
        return price != null ? ResponseEntity.ok(price) : ResponseEntity.notFound().build();
    }
}

