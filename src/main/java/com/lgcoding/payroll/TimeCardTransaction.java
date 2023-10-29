package com.lgcoding.payroll;

import java.time.LocalDate;

public class TimeCardTransaction implements Transaction {

    private final Integer empId;
    private final LocalDate date;
    private final Double hours;

    private final PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();

    public TimeCardTransaction(Integer empId, LocalDate date, Double hours) {
        this.empId = empId;
        this.date = date;
        this.hours = hours;
    }

    @Override
    public void execute() {
        var e = payrollDatabase.getEmployee(empId);
        if (e != null) {
            var pc = e.getPaymentClassification();
            if (pc instanceof HourlyClassification hc) {
                hc.addTimeCard(date, hours);
            } else {
                throw new IllegalStateException("Tried to add timecard to non-hourly employee");
            }
        } else {
            throw new IllegalStateException("Employee not found");
        }
    }
}