package com.inditex.pricing.infrastructure.persistence;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PricePK implements Serializable {
    private Integer brandId;
    private Integer productId;
    private Integer priceList;

    public PricePK(Integer brandId, Integer productId, Integer priceList) {
        this.brandId = brandId;
        this.productId = productId;
        this.priceList = priceList;
    }

    public PricePK() {
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPriceList() {
        return priceList;
    }

    public void setPriceList(Integer priceList) {
        this.priceList = priceList;
    }
}