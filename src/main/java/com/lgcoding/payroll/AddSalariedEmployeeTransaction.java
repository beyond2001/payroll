package com.lgcoding.payroll;

import lombok.Getter;

@Getter
public class AddSalariedEmployeeTransaction extends AddEmployeeTransaction {

    private final Double salary;

    public AddSalariedEmployeeTransaction(Integer empId, String name, String address, Double salary
    ) {
        super(empId, name, address);
        this.salary = salary;
    }

    @Override
    protected PaymentClassification getClassification() {
        return new SalariedClassification(salary);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }

}