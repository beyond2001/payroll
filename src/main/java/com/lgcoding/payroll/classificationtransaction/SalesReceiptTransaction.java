package com.lgcoding.payroll.classificationtransaction;

import com.lgcoding.payroll.classifications.CommissionedClassification;
import com.lgcoding.payroll.PayrollDatabase;
import com.lgcoding.payroll.Transaction;

import java.time.LocalDate;

public class SalesReceiptTransaction implements Transaction {

    private final Integer empId;
    private final LocalDate date;
    private final Double amount;

    private final PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();

    public SalesReceiptTransaction(Integer empId, LocalDate date, Double amount) {
        this.empId = empId;
        this.date = date;
        this.amount = amount;
    }

    @Override
    public void execute() {
        var e = payrollDatabase.getEmployee(empId);
        if (e != null) {
            var pc = e.getPaymentClassification();
            if (pc instanceof CommissionedClassification cc) {
                cc.addSalesReceipt(date, amount);
            } else {
                throw new IllegalStateException("Tried to add salesreceipt to non-commissioned employee");
            }
        } else {
            throw new IllegalStateException("Employee not found");
        }
    }
}