package com.lgcoding.payroll.generaltransactions;

import com.lgcoding.payroll.domain.Employee;
import com.lgcoding.payroll.PayrollDatabase;
import com.lgcoding.payroll.Transaction;

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