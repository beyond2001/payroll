package com.lgcoding.payroll;

public class ChangeEmployeeHoldTransaction extends ChangeEmployeeMethodTransaction {

    public ChangeEmployeeHoldTransaction(Integer empId) {
        super(empId);
    }

    @Override
    protected PaymentMethod getMethod() {
        return new HoldMethod();
    }
}