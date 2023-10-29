package com.lgcoding.payroll;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeeklySchedule implements PaymentSchedule {

    @Override
    public boolean isPayDate(LocalDate payDate) {
        return payDate.getDayOfWeek() == DayOfWeek.FRIDAY;
    }

    @Override
    public LocalDate getStartDate(LocalDate payDate) {
        return payDate.minusDays(6);
    }
}