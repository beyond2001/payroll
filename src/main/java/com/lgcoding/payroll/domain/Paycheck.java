package com.lgcoding.payroll.domain;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.time.LocalDate;

@Getter
public class Paycheck {

    private final LocalDate payDate;
    @Setter
    private LocalDate startDate;
    @Setter
    private Double grossPay;
    @Setter
    private Double deductions;
    @Setter
    private Double netPay;

    public Paycheck(LocalDate payDate) {
        this.payDate = payDate;
    }
}