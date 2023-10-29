package com.lgcoding.payroll;

public abstract class ChangeEmployeeTransaction implements Transaction {

    private final Integer empId;

    private final PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();

    public ChangeEmployeeTransaction(Integer empId) {
        this.empId = empId;
    }

    @Override
    public void execute() {
        var e = payrollDatabase.getEmployee(empId);
        if (e != null) {
            change(e);
        } else {
            throw new IllegalStateException("Employee not found");
        }
    }

    protected abstract void change(Employee e);
}