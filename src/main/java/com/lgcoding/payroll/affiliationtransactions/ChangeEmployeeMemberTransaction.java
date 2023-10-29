package com.lgcoding.payroll.affiliationtransactions;

import com.lgcoding.payroll.PayrollDatabase;
import com.lgcoding.payroll.affiliations.UnionAffiliation;
import com.lgcoding.payroll.affiliationtransactions.ChangeEmployeeAffiliationTransaction;
import com.lgcoding.payroll.domain.Affiliation;
import com.lgcoding.payroll.domain.Employee;

public class ChangeEmployeeMemberTransaction extends ChangeEmployeeAffiliationTransaction {

    private final Double dues;
    private final Integer memberId;

    public ChangeEmployeeMemberTransaction(Integer empId, Integer memberId, Double dues) {
        super(empId);
        this.memberId = memberId;
        this.dues = dues;
    }

    @Override
    protected Affiliation getAffiliation() {
        return new UnionAffiliation(memberId, dues);
    }

    @Override
    protected void recordMembership(Employee e) {
        PayrollDatabase.getInstance().addUnionMember(memberId, e);
    }
}