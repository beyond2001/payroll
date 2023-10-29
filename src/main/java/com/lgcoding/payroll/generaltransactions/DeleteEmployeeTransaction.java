package com.lgcoding.payroll.generaltransactions;

import com.lgcoding.payroll.PayrollDatabase;
import com.lgcoding.payroll.Transaction;

public class DeleteEmployeeTransaction implements Transaction {

    private final Integer empId;

    private final PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();

    public DeleteEmployeeTransaction(Integer empId) {
        this.empId = empId;
    }

    @Override
    public void execute() {
        payrollDatabase.deleteEmployee(empId);
    }
}