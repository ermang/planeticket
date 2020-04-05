package com.eg.planeticket.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceService {

    public BigDecimal calculatePrice(BigDecimal basePrice, Long newCapacity, Long maxCapacity) {
        BigDecimal newCapacityRatio = new BigDecimal(newCapacity).divide(new BigDecimal(maxCapacity));
        newCapacityRatio = newCapacityRatio.setScale(1, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal newPrice = basePrice;

        if(newCapacityRatio.compareTo(new BigDecimal("0.9")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.9"));
        else if(newCapacityRatio.compareTo(new BigDecimal("0.8")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.8"));
        else if(newCapacityRatio.compareTo(new BigDecimal("0.7")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.7"));
        else if(newCapacityRatio.compareTo(new BigDecimal("0.6")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.6"));
        else if(newCapacityRatio.compareTo(new BigDecimal("0.5")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.5"));
        else if(newCapacityRatio.compareTo(new BigDecimal("0.4")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.4"));
        else if(newCapacityRatio.compareTo(new BigDecimal("0.3")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.3"));
        else if(newCapacityRatio.compareTo(new BigDecimal("0.2")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.2"));
        else if(newCapacityRatio.compareTo(new BigDecimal("0.1")) > -1)
            newPrice = newPrice.multiply(new BigDecimal("1.1"));
        return newPrice;
    }

    public boolean isPriceReCalculationRequired(Long currentCapacity, Long newCapacity, Long maxCapacity) {

        BigDecimal currentCapacityRatio = new BigDecimal(currentCapacity).divide(new BigDecimal(maxCapacity));
        currentCapacityRatio = currentCapacityRatio.setScale(1, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal newCapacityRatio = new BigDecimal(newCapacity).divide(new BigDecimal(maxCapacity));
        newCapacityRatio = newCapacityRatio.setScale(1, BigDecimal.ROUND_HALF_DOWN);

        return currentCapacityRatio.compareTo(newCapacityRatio) != 0;
    }
}
