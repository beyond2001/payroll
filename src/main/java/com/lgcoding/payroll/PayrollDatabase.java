package com.lgcoding.payroll;

import com.lgcoding.payroll.domain.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayrollDatabase {
    private static final PayrollDatabase instance = new PayrollDatabase();
    private static final Map<Integer, Employee> employees = new HashMap<>();
    private static final Map<Integer, Employee> affiliationMembers = new HashMap<>();

    private PayrollDatabase() {
    }

    public static PayrollDatabase getInstance() {
        return instance;
    }

    public Employee getEmployee(int empId) {
        return employees.get(empId);
    }

    public void addEmployee(int empId, Employee e) {
        employees.put(empId, e);
    }

    public Employee deleteEmployee(int empId) {
        var e = getEmployee(empId);
        employees.remove(empId);
        return e;
    }

    public void addUnionMember(int memberId, Employee e) {
        affiliationMembers.put(memberId, e);
    }

    public Employee getAffiliationMember(int memberId) {
        return affiliationMembers.get(memberId);
    }

    public void deleteUnionMember(int memberId) {
        affiliationMembers.remove(memberId);
    }

    public List<Integer> getAllEmployeeIds() {
        return new ArrayList<Integer>(employees.keySet());
    }
}
