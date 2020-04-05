package com.eg.planeticket;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalOpsTest {

    @Test
    public void test1(){
        BigDecimal currentCapacityRatio = new BigDecimal("1.1");
        BigDecimal newCapacityRatio = new BigDecimal("1.2");

        Assert.assertEquals(-1, currentCapacityRatio.compareTo(newCapacityRatio));
    }

    @Test
    public void test2() {
        BigDecimal x = new BigDecimal("1.12345");
        x= x.setScale(1, BigDecimal.ROUND_HALF_DOWN);


        Assert.assertEquals("1.1", x.toString());
    }
}
