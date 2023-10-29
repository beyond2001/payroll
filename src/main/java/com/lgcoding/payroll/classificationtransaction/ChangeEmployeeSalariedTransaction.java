package com.lgcoding.payroll.classificationtransaction;

import com.lgcoding.payroll.classifications.SalariedClassification;
import com.lgcoding.payroll.domain.PaymentClassification;
import com.lgcoding.payroll.domain.PaymentSchedule;
import com.lgcoding.payroll.schedules.MonthlySchedule;

public class ChangeEmployeeSalariedTransaction extends ChangeEmployeeClassificationTransaction {

    private final Double salary;

    public ChangeEmployeeSalariedTransaction(Integer empId, Double salary) {
        super(empId);
        this.salary = salary;
    }

    @Override
    protected PaymentClassification getClassification() {
        return new SalariedClassification(salary);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }

}