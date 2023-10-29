package com.lgcoding.payroll.classifications;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SalesReceipt {

    private final LocalDate date;
    private final Double amount;

    public SalesReceipt(LocalDate date, Double amount) {
        this.date = date;
        this.amount = amount;
    }
}