package com.lgcoding.payroll.affiliationtransactions;

import com.lgcoding.payroll.PayrollDatabase;
import com.lgcoding.payroll.Transaction;
import com.lgcoding.payroll.affiliations.UnionAffiliation;
import com.lgcoding.payroll.domain.Affiliation;

import java.time.LocalDate;

public class ServiceChargeTransaction implements Transaction {

    private final int memberId;
    private final LocalDate date;
    private final Double amount;

    private final PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();

    public ServiceChargeTransaction(int memberId, LocalDate date, Double amount) {
        this.memberId = memberId;
        this.date = date;
        this.amount = amount;
    }

    @Override
    public void execute() {
        var e = payrollDatabase.getAffiliationMember(memberId);
        Affiliation af = e.getAffiliation();
        if (af instanceof UnionAffiliation uf) {
            uf.addServiceCharge(date, amount);
        }
    }

}