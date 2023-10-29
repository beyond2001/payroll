package com.lgcoding.payroll;

import java.time.LocalDate;

public interface PaymentSchedule {

    boolean isPayDate(LocalDate payDate);

    LocalDate getStartDate(LocalDate payDate);
}