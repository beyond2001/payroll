package com.lgcoding.payroll.methodtransactions;

import com.lgcoding.payroll.domain.Employee;
import com.lgcoding.payroll.domain.PaymentMethod;
import com.lgcoding.payroll.generaltransactions.ChangeEmployeeTransaction;

public abstract class ChangeEmployeeMethodTransaction extends ChangeEmployeeTransaction {

    public ChangeEmployeeMethodTransaction(Integer empId) {
        super(empId);
    }

    @Override
    protected void change(Employee e) {
        e.setPaymentMethod(getMethod());
    }

    protected abstract PaymentMethod getMethod();
}