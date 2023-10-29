package com.lgcoding.payroll.affiliations;


import com.lgcoding.payroll.domain.Paycheck;
import com.lgcoding.payroll.domain.Affiliation;

/**
 * Null Object for non affiliated employees
 */
public class NoAffiliation implements Affiliation {

    @Override
    public Double calculateDeductions(Paycheck paycheck) {
        return 0.0;
    }
}