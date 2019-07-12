package com.lei.active.Statute;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @Author: leiyunlong
 * @Date: 2019/7/11 11:34
 * @Version 1.0
 */
public class Aggregate {
    public static void main(String[] args) {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        float diff = 1e-6f;
        System.out.println(diff);
        if (a == b) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
        if (Math.abs(a - b) < diff) {
            System.out.println("两个浮点数在指定误差范围后相等");
        }
    }

    @Test
    public void method() {
        Float s = 0.1f;
        s.toString();
        BigDecimal a = new BigDecimal("1.0");
        BigDecimal b = new BigDecimal("1.0");
        BigDecimal c = new BigDecimal("1.0");
        BigDecimal x = a.subtract(b);
        BigDecimal y = b.subtract(c);
        System.out.println(x + "    " + y);
        if(x.equals(y)){
            System.out.println("true");
        }
    }

}
