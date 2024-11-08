package com.bh.calculator;

import org.springframework.stereotype.Service;

@Service
public class Calculator {
    public Integer sum(Integer a, Integer b){
        return a+b;
    }
}