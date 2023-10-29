package com.lgcoding.payroll;

public class ChangeEmployeeNameTransaction extends ChangeEmployeeTransaction {

    private final String name;

    public ChangeEmployeeNameTransaction(Integer empId, String name) {
        super(empId);
        this.name = name;
    }

    @Override
    protected void change(Employee e) {
        e.changeName(name);
    }
}