package com.lgcoding.payroll.classificationtransaction;

import com.lgcoding.payroll.classifications.HourlyClassification;
import com.lgcoding.payroll.domain.PaymentClassification;
import com.lgcoding.payroll.domain.PaymentSchedule;
import com.lgcoding.payroll.schedules.WeeklySchedule;

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