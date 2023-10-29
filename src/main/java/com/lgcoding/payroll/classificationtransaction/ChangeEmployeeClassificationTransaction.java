package com.lgcoding.payroll.classificationtransaction;

import com.lgcoding.payroll.generaltransactions.ChangeEmployeeTransaction;
import com.lgcoding.payroll.domain.Employee;
import com.lgcoding.payroll.domain.PaymentClassification;
import com.lgcoding.payroll.domain.PaymentSchedule;

public abstract class ChangeEmployeeClassificationTransaction extends ChangeEmployeeTransaction {

    public ChangeEmployeeClassificationTransaction(Integer empId) {
        super(empId);
    }

    @Override
    protected void change(Employee e) {
        e.setPaymentClassification(getClassification());
        e.setPaymentSchedule(getSchedule());
    }

    protected abstract PaymentClassification getClassification();

    protected abstract PaymentSchedule getSchedule();
}