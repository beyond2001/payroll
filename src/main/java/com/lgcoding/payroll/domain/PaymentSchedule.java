package com.lgcoding.payroll.domain;

import java.time.LocalDate;

public interface PaymentSchedule {

    boolean isPayDate(LocalDate payDate);

    LocalDate getStartDate(LocalDate payDate);
}