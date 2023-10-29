package com.lgcoding.payroll;

import lombok.Getter;

@Getter
public class SalariedClassification implements PaymentClassification {

    private final Double salary;

    public SalariedClassification(Double salary) {
        this.salary = salary;
    }

    @Override
    public Double calculatePay(Paycheck paycheck) {
        return salary;
    }
}