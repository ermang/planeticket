package com.eg.planeticket;

import com.eg.planeticket.service.PriceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class PriceServiceTest {

    private PriceService priceService;

    @Before
    public void setup(){
        priceService = new PriceService();
    }

    @Test
    public void is_price_calculation_required_V1(){
        boolean result = priceService.isPriceReCalculationRequired(0L, 1L, 10L);

        Assert.assertTrue(result);
    }

    @Test
    public void is_price_calculation_required_V2(){
        boolean result = priceService.isPriceReCalculationRequired(4L, 5L, 10L);

        Assert.assertTrue(result);
    }

    @Test
    public void calculate_price_V1() {
        BigDecimal result = priceService.calculatePrice(BigDecimal.TEN, 1L, 10L);

        Assert.assertTrue(new BigDecimal("11").compareTo(result) == 0);
    }

    @Test
    public void calculate_price_V2() {
        BigDecimal result = priceService.calculatePrice(BigDecimal.TEN, 2L, 10L);

        Assert.assertTrue(new BigDecimal("12").compareTo(result) == 0);
    }

    @Test
    public void calculate_price_V3() {
        BigDecimal result = priceService.calculatePrice(BigDecimal.TEN, 3L, 10L);

        Assert.assertTrue(new BigDecimal("13").compareTo(result) == 0);
    }

    @Test
    public void calculate_price_V4() {
        BigDecimal result = priceService.calculatePrice(BigDecimal.TEN, 4L, 10L);

        Assert.assertTrue(new BigDecimal("14").compareTo(result) == 0);
    }
}
