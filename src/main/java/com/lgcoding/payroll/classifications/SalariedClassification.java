package com.lgcoding.payroll.classifications;

import com.lgcoding.payroll.domain.Paycheck;
import com.lgcoding.payroll.domain.PaymentClassification;
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