package com.lgcoding.payroll.classifications;

import com.lgcoding.payroll.domain.Paycheck;
import com.lgcoding.payroll.domain.PaymentClassification;
import com.lgcoding.util.DateUtil;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
public class CommissionedClassification implements PaymentClassification {

    private final Double salary;
    private final Double commissionRate;
    private final Map<String, SalesReceipt> salesReceipts = new HashMap<String, SalesReceipt>();

    public CommissionedClassification(Double salary, Double commissionRate) {
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    public SalesReceipt addSalesReceipt(LocalDate date, Double amount) {
        var sr = new SalesReceipt(date, amount);
        salesReceipts.put(date.toString(), sr);
        return sr;
    }

    public SalesReceipt getSalesReceipt(LocalDate date) {
        return salesReceipts.get(date.toString());
    }

    @Override
    public Double calculatePay(Paycheck paycheck) {
        return salary + commissions(paycheck);
    }

    private Double commissions(Paycheck paycheck) {
        return salesReceipts.values().stream().filter(sr -> DateUtil.isBetween(sr.getDate(), paycheck.getStartDate(),
                        paycheck.getPayDate()))
                .map(sr -> calculateSalesReceiptCommission(sr)).reduce(0.0, (a, b) -> a + b);
    }

    private Double calculateSalesReceiptCommission(SalesReceipt salesReceipt) {
        return salesReceipt.getAmount() * commissionRate;
    }
}