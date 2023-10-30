package com.lgcoding.payroll.generaltransactions;

import com.lgcoding.payroll.domain.Employee;
import com.lgcoding.payroll.domain.Paycheck;
import com.lgcoding.payroll.PayrollDatabase;
import com.lgcoding.payroll.Transaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PaydayTransaction implements Transaction {

    private final LocalDate payDate;
    private final Map<Integer, Paycheck> paychecks = new HashMap<>();

    public PaydayTransaction(LocalDate payDate) {
        this.payDate = payDate;
    }

    @Override
    public void execute() {
        var empIds = PayrollDatabase.getInstance().getAllEmployeeIds();
        empIds.forEach(empId -> {
            Employee e = PayrollDatabase.getInstance().getEmployee(empId);
            if (e != null) {
                if (e.isPayDate(payDate)) {
                    Paycheck pc = new Paycheck(payDate);
                    e.payday(pc);
                    paychecks.put(empId, pc);
                }
            }
        });
    }

    public Paycheck getPaycheck(Integer empId) {
        return paychecks.get(empId);
    }
}