package com.lgcoding.payroll.generaltransactions;

import com.lgcoding.payroll.classifications.HourlyClassification;
import com.lgcoding.payroll.domain.PaymentClassification;
import com.lgcoding.payroll.domain.PaymentSchedule;
import com.lgcoding.payroll.schedules.WeeklySchedule;

public class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {

    private final Double hourlyRate;

    public AddHourlyEmployeeTransaction(Integer itsEmpId, String itsName, String itsAddress, Double hourlyRate) {
        super(itsEmpId, itsName, itsAddress);
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