package com.lgcoding.payroll;

public class ChangeEmployeeAddressTransaction extends ChangeEmployeeTransaction {

    private final String name;

    public ChangeEmployeeAddressTransaction(Integer empId, String name) {
        super(empId);
        this.name = name;
    }

    @Override
    protected void change(Employee e) {
        e.changeAddress(name);
    }
}