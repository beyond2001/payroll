package com.lgcoding.payroll;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ServiceCharge {

    private final LocalDate date;
    private final Double amount;

    public ServiceCharge(LocalDate date, Double amount) {
        this.date = date;
        this.amount = amount;
    }
}