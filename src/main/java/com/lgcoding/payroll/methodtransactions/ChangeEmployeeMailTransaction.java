package com.lgcoding.payroll.methodtransactions;

import com.lgcoding.payroll.domain.PaymentMethod;
import com.lgcoding.payroll.methods.MailMethod;

public class ChangeEmployeeMailTransaction extends ChangeEmployeeMethodTransaction {

    public ChangeEmployeeMailTransaction(Integer empId) {
        super(empId);
    }

    @Override
    protected PaymentMethod getMethod() {
        return new MailMethod();
    }

}