package com.lgcoding.payroll.classificationtransaction;

import com.lgcoding.payroll.schedules.BiweeklySchedule;
import com.lgcoding.payroll.classifications.CommissionedClassification;
import com.lgcoding.payroll.domain.PaymentClassification;
import com.lgcoding.payroll.domain.PaymentSchedule;

public class ChangeEmployeeCommissionedTransaction extends ChangeEmployeeClassificationTransaction {

    private final Double salary;
    private final Double commissionRate;

    public ChangeEmployeeCommissionedTransaction(int empId, Double salary, Double commissionRate) {
        super(empId);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return new CommissionedClassification(salary, commissionRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }

}