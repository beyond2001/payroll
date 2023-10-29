package com.lgcoding.payroll.generaltransactions;

import com.lgcoding.payroll.schedules.MonthlySchedule;
import com.lgcoding.payroll.domain.PaymentClassification;
import com.lgcoding.payroll.domain.PaymentSchedule;
import com.lgcoding.payroll.classifications.SalariedClassification;
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