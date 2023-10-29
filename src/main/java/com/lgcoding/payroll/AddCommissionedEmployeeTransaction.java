package com.lgcoding.payroll;

public class AddCommissionedEmployeeTransaction extends AddEmployeeTransaction {

    private final Double salary;
    private final Double commissionRate;

    public AddCommissionedEmployeeTransaction(Integer itsEmpId, String itsName, String itsAddress, Double salary,
                                              Double commissionRate) {
        super(itsEmpId, itsName, itsAddress);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return new CommissionedClassification(salary, commissionRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }

}