package com.lgcoding.payroll.schedules;

import com.lgcoding.payroll.domain.PaymentSchedule;
import com.lgcoding.util.DateUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class BiweeklySchedule implements PaymentSchedule {

    @Override
    public boolean isPayDate(LocalDate payDate) {
        return (payDate.getDayOfWeek() == DayOfWeek.FRIDAY) && DateUtil.isInBiweekly(payDate);
    }

    @Override
    public LocalDate getStartDate(LocalDate payDate) {
        return payDate.minusDays(13);
    }
}