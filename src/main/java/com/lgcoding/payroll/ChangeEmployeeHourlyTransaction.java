package com.lgcoding.payroll;

public class ChangeEmployeeHourlyTransaction extends ChangeEmployeeClassificationTransaction {

    private final Double hourlyRate;

    public ChangeEmployeeHourlyTransaction(Integer empId, Double hourlyRate) {
        super(empId);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return new HourlyClassification(hourlyRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }

}