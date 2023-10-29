package com.lgcoding.payroll.methodtransactions;

import com.lgcoding.payroll.domain.PaymentMethod;
import com.lgcoding.payroll.methods.DirectMethod;

public class ChangeEmployeeDirectTransaction extends ChangeEmployeeMethodTransaction {

    public ChangeEmployeeDirectTransaction(Integer empId) {
        super(empId);
    }

    @Override
    protected PaymentMethod getMethod() {
        return new DirectMethod();
    }

}