package com.lgcoding.payroll.affiliationtransactions;

import com.lgcoding.payroll.affiliations.NoAffiliation;
import com.lgcoding.payroll.PayrollDatabase;
import com.lgcoding.payroll.affiliations.UnionAffiliation;
import com.lgcoding.payroll.domain.Affiliation;
import com.lgcoding.payroll.domain.Employee;

public class ChangeEmployeeUnaffiliatedTransaction extends ChangeEmployeeAffiliationTransaction {

    public ChangeEmployeeUnaffiliatedTransaction(Integer empId) {
        super(empId);
    }

    @Override
    protected Affiliation getAffiliation() {
        return new NoAffiliation();
    }

    @Override
    protected void recordMembership(Employee e) {
        var af = e.getAffiliation();
        if (af instanceof UnionAffiliation) {
            int memberId = ((UnionAffiliation) af).getMemberId();
            PayrollDatabase.getInstance().deleteUnionMember(memberId);
        }
    }

}