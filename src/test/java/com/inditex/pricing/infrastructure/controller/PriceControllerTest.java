package com.inditex.pricing.infrastructure.controller;

import com.inditex.pricing.application.PriceService;
import com.inditex.pricing.domain.Price;
import com.inditex.pricing.infrastructure.dto.PriceResponse;
import com.inditex.pricing.infrastructure.mapper.PriceMapperManual;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @MockBean
    private PriceMapperManual priceMapperManual;

    // Test 1: 10:00 del día 14 → tarifa 1, precio 35.50
    @Test
    @SneakyThrows
    @DisplayName("Test 1: GET /prices at 10:00 on June 14 returns tariff 1 and price 35.50")
    void testGetPriceAt10OnJune14() {
        var priceDomain = createPriceDomainForTariff1();
        var priceResponse = createPriceResponseForTariff1();

        given(priceService.getApplicablePrice(35455, 1, LocalDateTime.parse("2020-06-14T10:00:00")))
                .willReturn(priceDomain);
        given(priceMapperManual.priceResponseToPrice(priceDomain))
                .willReturn(priceResponse);

        mockMvc.perform(get("/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(1)))
                .andExpect(jsonPath("$.price", is(35.50)));

        verify(priceMapperManual, times(1)).priceResponseToPrice(priceDomain);
    }

    // Test 2: 16:00 del día 14 → tarifa 2, precio 25.45
    @Test
    @SneakyThrows
    @DisplayName("Test 2: GET /prices at 16:00 on June 14 returns tariff 2 and price 25.45")
    void testGetPriceAt16OnJune14() {
        Price priceDomain = createPriceDomainForTariff2();
        PriceResponse priceResponse = createPriceResponseForTariff2();

        given(priceService.getApplicablePrice(35455, 1, LocalDateTime.parse("2020-06-14T16:00:00")))
                .willReturn(priceDomain);
        given(priceMapperManual.priceResponseToPrice(priceDomain))
                .willReturn(priceResponse);

        mockMvc.perform(get("/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(2)))
                .andExpect(jsonPath("$.price", is(25.45)));

        verify(priceMapperManual, times(1)).priceResponseToPrice(priceDomain);
    }

    // Test 3: 21:00 del día 14 → tarifa 1, precio 35.50 (porque tarifa 2 aplica solo de 15:00 a 18:30)
    @Test
    @SneakyThrows
    @DisplayName("Test 3: GET /prices at 21:00 on June 14 returns tariff 1 and price 35.50")
    void testGetPriceAt21OnJune14() {
        Price priceDomain = createPriceDomainForTariff1();
        PriceResponse priceResponse = createPriceResponseForTariff1();

        given(priceService.getApplicablePrice(35455, 1, LocalDateTime.parse("2020-06-14T21:00:00")))
                .willReturn(priceDomain);
        given(priceMapperManual.priceResponseToPrice(priceDomain))
                .willReturn(priceResponse);

        mockMvc.perform(get("/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-14T21:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(1)))
                .andExpect(jsonPath("$.price", is(35.50)));

        verify(priceMapperManual, times(1)).priceResponseToPrice(priceDomain);
    }

    // Test 4: 10:00 del día 15 → tarifa 3, precio 30.50
    @Test
    @SneakyThrows
    @DisplayName("Test 4: GET /prices at 10:00 on June 15 returns tariff 3 and price 30.50")
    void testGetPriceAt10OnJune15() {
        Price priceDomain = createPriceDomainForTariff3();
        PriceResponse priceResponse = createPriceResponseForTariff3();

        given(priceService.getApplicablePrice(35455, 1, LocalDateTime.parse("2020-06-15T10:00:00")))
                .willReturn(priceDomain);
        given(priceMapperManual.priceResponseToPrice(priceDomain))
                .willReturn(priceResponse);

        mockMvc.perform(get("/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-15T10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(3)))
                .andExpect(jsonPath("$.price", is(30.50)));

        verify(priceMapperManual, times(1)).priceResponseToPrice(priceDomain);
    }

    // Test 5: 21:00 del día 16 → tarifa 4, precio 38.95
    @Test
    @SneakyThrows
    @DisplayName("Test 5: GET /prices at 21:00 on June 16 returns tariff 4 and price 38.95")
    void testGetPriceAt21OnJune16() {
        Price priceDomain = createPriceDomainForTariff4();
        PriceResponse priceResponse = createPriceResponseForTariff4();

        given(priceService.getApplicablePrice(35455, 1, LocalDateTime.parse("2020-06-16T21:00:00")))
                .willReturn(priceDomain);
        given(priceMapperManual.priceResponseToPrice(priceDomain))
                .willReturn(priceResponse);

        mockMvc.perform(get("/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-16T21:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(4)))
                .andExpect(jsonPath("$.price", is(38.95)));

        verify(priceMapperManual, times(1)).priceResponseToPrice(priceDomain);
    }

    public static Price createPriceDomainForTariff1() {
        return Price.builder()
                .brandId(1)
                .productId(35455)
                .priceList(1)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priority(0)
                .price(35.50f)
                .currency("EUR")
                .build();
    }

    public static Price createPriceDomainForTariff2() {
        return Price.builder()
                .brandId(1)
                .productId(35455)
                .priceList(2)
                .startDate(LocalDateTime.parse("2020-06-14T15:00:00"))
                .endDate(LocalDateTime.parse("2020-06-14T18:30:00"))
                .priority(1)
                .price(25.45f)
                .currency("EUR")
                .build();
    }

    public static Price createPriceDomainForTariff3() {
        return Price.builder()
                .brandId(1)
                .productId(35455)
                .priceList(3)
                .startDate(LocalDateTime.parse("2020-06-15T00:00:00"))
                .endDate(LocalDateTime.parse("2020-06-15T11:00:00"))
                .priority(1)
                .price(30.50f)
                .currency("EUR")
                .build();
    }

    public static Price createPriceDomainForTariff4() {
        return Price.builder()
                .brandId(1)
                .productId(35455)
                .priceList(4)
                .startDate(LocalDateTime.parse("2020-06-15T16:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priority(1)
                .price(38.95f)
                .currency("EUR")
                .build();
    }

    // Métodos para crear objetos de respuesta (PriceResponse)

    public static PriceResponse createPriceResponseForTariff1() {
        return PriceResponse.builder()
                .brandId(1)
                .productId(35455)
                .priceList(1)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .price(35.50f)
                .build();
    }

    public static PriceResponse createPriceResponseForTariff2() {
        return PriceResponse.builder()
                .brandId(1)
                .productId(35455)
                .priceList(2)
                .startDate(LocalDateTime.parse("2020-06-14T15:00:00"))
                .endDate(LocalDateTime.parse("2020-06-14T18:30:00"))
                .price(25.45f)
                .build();
    }

    public static PriceResponse createPriceResponseForTariff3() {
        return PriceResponse.builder()
                .brandId(1)
                .productId(35455)
                .priceList(3)
                .startDate(LocalDateTime.parse("2020-06-15T00:00:00"))
                .endDate(LocalDateTime.parse("2020-06-15T11:00:00"))
                .price(30.50f)
                .build();
    }

    public static PriceResponse createPriceResponseForTariff4() {
        return PriceResponse.builder()
                .brandId(1)
                .productId(35455)
                .priceList(4)
                .startDate(LocalDateTime.parse("2020-06-15T16:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .price(38.95f)
                .build();
    }
}
