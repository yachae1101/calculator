package com.bh.calculator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalculatorController {
    private final Calculator calculator;

    @RequestMapping("/")
    String sum(@RequestParam("a") Integer a, @RequestParam("b") Integer b) {
        //
        return String.valueOf(calculator.sum(a, b));
    }
}