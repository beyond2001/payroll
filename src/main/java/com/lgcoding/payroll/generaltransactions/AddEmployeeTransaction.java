package com.lgcoding.payroll.generaltransactions;

import com.lgcoding.payroll.*;
import com.lgcoding.payroll.affiliations.NoAffiliation;
import com.lgcoding.payroll.domain.Employee;
import com.lgcoding.payroll.domain.PaymentClassification;
import com.lgcoding.payroll.domain.PaymentSchedule;
import com.lgcoding.payroll.methods.HoldMethod;
import lombok.Getter;

@Getter
public abstract class AddEmployeeTransaction implements Transaction {

    private final Integer empId;
    private final String name;
    private final String address;

    private final PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();

    public AddEmployeeTransaction(Integer empId, String name, String address) {
        this.empId = empId;
        this.name = name;
        this.address = address;
    }

    public void execute() {
        var e = new Employee(empId, name, address);
        e.setPaymentClassification(getClassification());
        e.setPaymentSchedule(getSchedule());
        e.setPaymentMethod(new HoldMethod());
        e.setAffiliation(new NoAffiliation());
        this.payrollDatabase.addEmployee(empId, e);
    }

    protected abstract PaymentClassification getClassification();

    protected abstract PaymentSchedule getSchedule();
}