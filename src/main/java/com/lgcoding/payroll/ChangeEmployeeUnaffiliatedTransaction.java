package com.lgcoding.payroll;

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