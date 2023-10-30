package com.lgcoding.payroll.classifications;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TimeCard {

    private final LocalDate date;
    private final double hours;

    public TimeCard(LocalDate date, double hours) {
        this.date = date;
        this.hours = hours;
    }
}