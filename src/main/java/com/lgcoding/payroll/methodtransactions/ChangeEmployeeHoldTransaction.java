package com.lgcoding.payroll.methodtransactions;

import com.lgcoding.payroll.domain.PaymentMethod;
import com.lgcoding.payroll.methods.HoldMethod;

public class ChangeEmployeeHoldTransaction extends ChangeEmployeeMethodTransaction {

    public ChangeEmployeeHoldTransaction(Integer empId) {
        super(empId);
    }

    @Override
    protected PaymentMethod getMethod() {
        return new HoldMethod();
    }
}