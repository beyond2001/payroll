package com.lgcoding.payroll;


/**
 * Null Object for non affiliated employees
 */
public class NoAffiliation implements Affiliation {

    @Override
    public Double calculateDeductions(Paycheck paycheck) {
        return 0.0;
    }
}