package com.lgcoding.payroll.schedules;


import com.lgcoding.payroll.domain.PaymentSchedule;

import java.time.LocalDate;

public class MonthlySchedule implements PaymentSchedule {

    @Override
    public boolean isPayDate(LocalDate payDate) {
        return isLastDayOfMonth(payDate);
    }

    @Override
    public LocalDate getStartDate(LocalDate payDate) {
        return payDate.withDayOfMonth(1);
    }

    private boolean isLastDayOfMonth(LocalDate date) {
        return date.getDayOfMonth() == date.lengthOfMonth();
    }
}