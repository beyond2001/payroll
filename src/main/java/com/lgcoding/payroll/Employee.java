package com.lgcoding.payroll;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class Employee {
    private final int id;
    private String name;
    private String address;
    @Setter
    private PaymentMethod paymentMethod;
    @Setter
    private PaymentClassification paymentClassification;
    @Setter
    private PaymentSchedule paymentSchedule;
    @Setter
    private Affiliation affiliation;

    public Employee(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public boolean isPayDate(LocalDate payDate) {
        return paymentSchedule.isPayDate(payDate);
    }

    public void payday(Paycheck paycheck) {
        LocalDate startDate = paymentSchedule.getStartDate(paycheck.getPayDate());
        paycheck.setStartDate(startDate);
        Double grossPay = paymentClassification.calculatePay(paycheck);
        Double deductions = affiliation.calculateDeductions(paycheck);
        Double netPay = grossPay - deductions;
        paycheck.setGrossPay(grossPay);
        paycheck.setDeductions(deductions);
        paycheck.setNetPay(netPay);
    }
}
