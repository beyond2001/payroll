package com.lgcoding.payroll;

import com.lgcoding.util.DateUtil;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
public class UnionAffiliation implements Affiliation {

    private final Integer memberId;
    private final Double dues;
    private final Map<String, ServiceCharge> serviceCharges = new HashMap<String, ServiceCharge>();

    public UnionAffiliation(Integer memberId, Double dues) {
        this.memberId = memberId;
        this.dues = dues;
    }

    public ServiceCharge getServiceCharge(LocalDate date) {
        return serviceCharges.get(date.toString());
    }

    public ServiceCharge addServiceCharge(LocalDate date, double amount) {
        var sc = new ServiceCharge(date, amount);
        serviceCharges.put(date.toString(), sc);
        return sc;
    }

    @Override
    public Double calculateDeductions(Paycheck paycheck) {
        return (dues * numberOfDeductibleWeeks(paycheck)) + calculateServiceCharges(paycheck);
    }

    private int numberOfDeductibleWeeks(Paycheck paycheck) {
        var deductableWeeks = 0;

        for (var day = paycheck.getStartDate(); day.isBefore(paycheck.getPayDate().plusDays(1)); day = day.plusDays(1)) {
            if (day.getDayOfWeek() == DayOfWeek.FRIDAY) {
                deductableWeeks++;
            }
        }

        return deductableWeeks;
    }

    private Double calculateServiceCharges(Paycheck paycheck) {
        return serviceCharges.values().stream().filter(sc -> DateUtil.isBetween(sc.getDate(), paycheck.getStartDate(),
                        paycheck.getPayDate()))
                .map(sc -> sc.getAmount()).reduce(0.0, (a, b) -> a + b);
    }
}