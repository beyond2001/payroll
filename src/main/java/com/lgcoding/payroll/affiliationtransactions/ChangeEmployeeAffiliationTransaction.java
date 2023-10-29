package com.lgcoding.payroll.affiliationtransactions;

import com.lgcoding.payroll.domain.Affiliation;
import com.lgcoding.payroll.domain.Employee;
import com.lgcoding.payroll.generaltransactions.ChangeEmployeeTransaction;

public abstract class ChangeEmployeeAffiliationTransaction extends ChangeEmployeeTransaction {

    public ChangeEmployeeAffiliationTransaction(Integer empId) {
        super(empId);
    }

    @Override
    protected void change(Employee e) {
        recordMembership(e);
        e.setAffiliation(getAffiliation());
    }

    protected abstract Affiliation getAffiliation();

    protected abstract void recordMembership(Employee e);
}