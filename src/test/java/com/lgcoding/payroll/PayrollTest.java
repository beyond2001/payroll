package com.lgcoding.payroll;

import com.lgcoding.payroll.affiliations.ServiceCharge;
import com.lgcoding.payroll.affiliations.UnionAffiliation;
import com.lgcoding.payroll.affiliationtransactions.ChangeEmployeeMemberTransaction;
import com.lgcoding.payroll.affiliationtransactions.ChangeEmployeeUnaffiliatedTransaction;
import com.lgcoding.payroll.affiliationtransactions.ServiceChargeTransaction;
import com.lgcoding.payroll.classifications.*;
import com.lgcoding.payroll.classificationtransaction.*;
import com.lgcoding.payroll.domain.Employee;
import com.lgcoding.payroll.domain.Paycheck;
import com.lgcoding.payroll.generaltransactions.*;
import com.lgcoding.payroll.methods.DirectMethod;
import com.lgcoding.payroll.methods.HoldMethod;
import com.lgcoding.payroll.methods.MailMethod;
import com.lgcoding.payroll.methodtransactions.ChangeEmployeeDirectTransaction;
import com.lgcoding.payroll.methodtransactions.ChangeEmployeeHoldTransaction;
import com.lgcoding.payroll.methodtransactions.ChangeEmployeeMailTransaction;
import com.lgcoding.payroll.schedules.BiweeklySchedule;
import com.lgcoding.payroll.schedules.MonthlySchedule;
import com.lgcoding.payroll.schedules.WeeklySchedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PayrollTest {

      PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();

    @Test
    public void testAddSalariedEmployee() {
        int empId = 1;
        var t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home", 1000.00);
        t.execute();

        var e = payrollDatabase.getEmployee(empId);
        assertEquals("Bob", e.getName());

        assertSalariedEmployee(e, 1000.00);
    }

    @Test
    public void testAddHourlyEmployee() {
        int empId = 1;
        var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 1.0);
        t.execute();

        var e = payrollDatabase.getEmployee(empId);
        assertHourlyEmployee(e, 1.0);
    }

    @Test
    public void testAddCommissionedEmployee() {
        int empId = 1;
        var t = new AddCommissionedEmployeeTransaction(empId, "Bob", "Home", 1000.0, 0.1);
        t.execute();

        var e = payrollDatabase.getEmployee(empId);
        assertCommissionedEmployee(e, 1000.0, 0.1);
    }

    @Test
    public void testDeleteEmployee() {
        int empId = 3;
        var t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home", 1000.0);
        t.execute();
        {
            var e = payrollDatabase.getEmployee(empId);
            assertNotNull(e);
        }

        var dt = new DeleteEmployeeTransaction(empId);
        dt.execute();
        {
            var e = payrollDatabase.getEmployee(empId);
            assertNull(e);
        }
    }

    @Test
    public void testTimeCardTransaction() {
        int empId = 3;
        var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 1.0);
        t.execute();

        var postingDate = LocalDate.now();
        var tct = new TimeCardTransaction(empId, postingDate, 8.0);
        tct.execute();

        var e = payrollDatabase.getEmployee(empId);

        var pc = (HourlyClassification) e.getPaymentClassification();
        TimeCard tc = pc.getTimeCard(postingDate);
        assertEquals(8.0, tc.getHours());
    }

    @Test
    public void testSalesReceiptTransaction() {
        int empId = 3;
        var t = new AddCommissionedEmployeeTransaction(empId, "Bob", "Home", 1000.0, 0.1);
        t.execute();

        var postingDate = LocalDate.now();
        var srt = new SalesReceiptTransaction(empId, postingDate, 79000.00);
        srt.execute();

        var e = payrollDatabase.getEmployee(empId);

        var pc = (CommissionedClassification) e.getPaymentClassification();
        SalesReceipt tc = pc.getSalesReceipt(postingDate);
        assertEquals(79000.00, tc.getAmount());
    }

    @Test
    public void testAddServiceCharge() {
        int empId = 2;
        int memberId = 86;
        var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 1.0);
        t.execute();

        var e = payrollDatabase.getEmployee(empId);
        UnionAffiliation af = new UnionAffiliation(memberId, 3.5);
        e.setAffiliation(af);

        payrollDatabase.addUnionMember(memberId, e);

        var postingDate = LocalDate.now();
        var sct = new ServiceChargeTransaction(memberId, postingDate, 12.95);
        sct.execute();

        ServiceCharge sc = af.getServiceCharge(postingDate);
        assertNotNull(sc);
        assertEquals(12.95, sc.getAmount());
    }

    @Test
    public void testChangeNameTransaction() {
        int empId = 2;
        var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 1.0);
        t.execute();

        var cnt = new ChangeEmployeeNameTransaction(empId, "Gustavo");
        cnt.execute();

        var e = payrollDatabase.getEmployee(empId);
        assertEquals("Gustavo", e.getName());
    }

    @Test
    public void testChangeAddressTransaction() {
        int empId = 2;
        var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 1.0);
        t.execute();

        var cnt = new ChangeEmployeeAddressTransaction(empId, "Casa");
        cnt.execute();

        var e = payrollDatabase.getEmployee(empId);
        assertEquals("Casa", e.getAddress());
    }

    @Test
    public void testChangeHourlyTransaction() {
        int empId = 3;
        var t = new AddCommissionedEmployeeTransaction(empId, "Bob", "Home", 1000.0, 1.0);
        t.execute();

        var cht = new ChangeEmployeeHourlyTransaction(empId, 2.5);
        cht.execute();

        var e = payrollDatabase.getEmployee(empId);
        assertHourlyEmployee(e, 2.5);
    }

    @Test
    public void testChangeSalariedTransaction() {
        int empId = 3;
        var t = new AddCommissionedEmployeeTransaction(empId, "Bob", "Home", 1000.0, 1.0);
        t.execute();

        var cst = new ChangeEmployeeSalariedTransaction(empId, 1500.00);
        cst.execute();

        var e = payrollDatabase.getEmployee(empId);
        assertSalariedEmployee(e, 1500.00);
    }

    @Test
    public void testChangeCommissionedTransaction() {
        int empId = 3;
        var t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home", 1000.0);
        t.execute();

        var cct = new ChangeEmployeeCommissionedTransaction(empId, 1500.00, 2.5);
        cct.execute();

        var e = payrollDatabase.getEmployee(empId);
        assertCommissionedEmployee(e, 1500.00, 2.5);
    }

    @Test
    public void testChangeDirectTransaction() {
        int empId = 1;
        var t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home", 1000.00);
        t.execute();

        var cdt = new ChangeEmployeeDirectTransaction(empId);
        cdt.execute();

        var e = payrollDatabase.getEmployee(empId);

        assertTrue(e.getPaymentMethod() instanceof DirectMethod);
    }

    @Test
    public void testChangeMailTransaction() {
        int empId = 1;
        var t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home", 1000.00);
        t.execute();

        var cmt = new ChangeEmployeeMailTransaction(empId);
        cmt.execute();

        var e = payrollDatabase.getEmployee(empId);

        assertTrue(e.getPaymentMethod() instanceof MailMethod);
    }

    @Test
    public void testChangeHoldTransaction() {
        int empId = 1;
        var t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home", 1000.00);
        t.execute();

        var cmt = new ChangeEmployeeMailTransaction(empId);
        cmt.execute();

        var cht = new ChangeEmployeeHoldTransaction(empId);
        cht.execute();

        var e = payrollDatabase.getEmployee(empId);
        assertTrue(e.getPaymentMethod() instanceof HoldMethod);
    }

    @Test
    public void testChangeMemberTransaction() {
        int empId = 2;
        int memberId = 1231;
        var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 1.0);
        t.execute();

        var cmt = new ChangeEmployeeMemberTransaction(empId, memberId, 99.42);
        cmt.execute();

        var e = payrollDatabase.getEmployee(empId);
        var af = (UnionAffiliation) e.getAffiliation();
        assertEquals(99.42, af.getDues());

        var m = payrollDatabase.getAffiliationMember(memberId);
        assertEquals(e, m);
    }

    @Test
    public void testChangeUnaffiliatedTransaction() {
        int empId = 2;
        int memberId = 1231;
        var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 1.0);
        t.execute();

        var cmt = new ChangeEmployeeMemberTransaction(empId, memberId, 99.42);
        cmt.execute();

        var ceu = new ChangeEmployeeUnaffiliatedTransaction(empId);
        ceu.execute();

        var m = payrollDatabase.getAffiliationMember(memberId);
        assertNull(m);
    }

    @Test
    public void testPaySingleSalariedEmployee() {
        {
            int empId = 1;
            var t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home", 1000.00);
            t.execute();

            LocalDate payDate = LocalDate.of(2001, 11, 30);
            var pt = new PaydayTransaction(payDate);
            pt.execute();

            Paycheck pc = pt.getPaycheck(empId);
            assertNotNull(pc);
            assertEquals(pc.getPayDate(), payDate);
            assertEquals(pc.getDeductions(), 0.0);
            assertEquals(pc.getNetPay(), 1000.00);
        }
        {
            int empId = 2;
            var t = new AddSalariedEmployeeTransaction(empId, "Gustavo", "Angola", 1500.00);
            t.execute();

            LocalDate payDate = LocalDate.of(2001, 11, 30);
            var pt = new PaydayTransaction(payDate);
            pt.execute();

            Paycheck pc = pt.getPaycheck(empId);
            assertNotNull(pc);
            assertEquals(pc.getPayDate(), payDate);
            assertEquals(pc.getDeductions(), 0.0);
            assertEquals(pc.getNetPay(), 1500.00);
        }
    }

    @Test
    public void testPaySingleSalariedEmployeeOnWrongDate() {
        int empId = 1;
        var t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home", 1000.00);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 29);
        var pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        assertNull(pc);
    }

    @Test
    public void testPaySingleHourlyEmployeeNoTimeCards() {
        int empId = 1;
        var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 13.45);
        t.execute();

        var payDate = LocalDate.of(2001, 11, 9); // Friday
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, 0.0);
    }

    @Test
    public void testPaySingleHourlyEmployeeWithTimeCards() {
        int empId = 1;
        {
            var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 13.45);
            t.execute();
        }
        {
            var workday = LocalDate.of(2001, 11, 5);
            var t = new TimeCardTransaction(empId, workday, 2.0);
            t.execute();
        }
        {
            var workday = LocalDate.of(2001, 11, 6);
            var t = new TimeCardTransaction(empId, workday, 8.0);
            t.execute();
        }

        var payDate = LocalDate.of(2001, 11, 9); // Friday
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, 134.5);
    }

    @Test
    public void testPaySingleHourlyEmployeeWithTimeCardsOvertime() {
        int empId = 1;
        {
            var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 13.45);
            t.execute();
        }
        {
            var workday = LocalDate.of(2001, 11, 5); // Monday
            var t = new TimeCardTransaction(empId, workday, 9.0);
            t.execute();
        }

        var payDate = LocalDate.of(2001, 11, 9); // Friday
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, 127.77);
    }

    @Test
    public void testPaySingleHourlyEmployeeOnWrongDate() {
        int empId = 1;
        {
            var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 13.45);
            t.execute();
        }
        {
            var workdayOne = LocalDate.of(2001, 11, 5);
            var t = new TimeCardTransaction(empId, workdayOne, 2.0);
            t.execute();
        }

        var payDate = LocalDate.of(2001, 11, 8); // Thursday
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertNull(pc);
    }

    @Test
    public void testPaySingleHourlyEmployeeWithTimeCardsSpanningPeriods() {
        int empId = 1;
        {
            var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 13.45);
            t.execute();
        }
        {
            var workday = LocalDate.of(2001, 11, 1); // last week
            var t = new TimeCardTransaction(empId, workday, 2.0);
            t.execute();
        }
        {
            var workday = LocalDate.of(2001, 11, 6);
            var t = new TimeCardTransaction(empId, workday, 8.0);
            t.execute();
        }

        var payDate = LocalDate.of(2001, 11, 9); // Friday
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, 107.6);
    }

    @Test
    public void testPaySingleHourlyEmployeeWeekendTimeCards() {
        int empId = 1;
        {
            var t = new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 13.45);
            t.execute();
        }
        {
            var workday = LocalDate.of(2001, 11, 3); // Saturday
            var t = new TimeCardTransaction(empId, workday, 6.0);
            t.execute();
        }
        {
            var workday = LocalDate.of(2001, 11, 6);
            var t = new TimeCardTransaction(empId, workday, 8.0);
            t.execute();
        }

        var payDate = LocalDate.of(2001, 11, 9); // Friday
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, 228.65);
    }

    @Test
    public void testPaySingleCommissionedEmployeeNoSalesReceipt() {
        int empId = 1;
        {
            var t = new AddCommissionedEmployeeTransaction(empId, "Bob", "Home", 1000.00, 1.4);
            t.execute();
        }

        var payDate = LocalDate.of(2001, 11, 2); // Friday
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, 1000.00);
    }

    @Test
    public void testPaySingleCommissionedEmployeeOnWrongDate() {
        int empId = 1;
        {
            var t = new AddCommissionedEmployeeTransaction(empId, "Bob", "Home", 1000.00, 1.4);
            t.execute();
        }
        {
            var payDate = LocalDate.of(2001, 11, 9); // Friday
            var pt = new PaydayTransaction(payDate);
            pt.execute();
            Paycheck pc = pt.getPaycheck(empId);
            assertNull(pc);
        }
        {
            var payDate = LocalDate.of(2001, 11, 13); // Thursday
            var pt = new PaydayTransaction(payDate);
            pt.execute();
            Paycheck pc = pt.getPaycheck(empId);
            assertNull(pc);
        }
    }

    @Test
    public void testPaySingleCommissionedEmployeeWithSalesReceipt() {
        int empId = 1;
        {
            var t = new AddCommissionedEmployeeTransaction(empId, "Bob", "Home", 1000.00, 0.003);
            t.execute();
        }
        {
            var t = new SalesReceiptTransaction(empId, LocalDate.of(2001, 11, 4), 79000.00);
            t.execute();
        }
        {
            var t = new SalesReceiptTransaction(empId, LocalDate.of(2001, 11, 14), 90000.00);
            t.execute();
        }

        var payDate = LocalDate.of(2001, 11, 16); // Friday
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, 1507.00);
    }

    @Test
    public void testPaySingleCommissionedEmployeeSalesReceiptInPayDay() {
        int empId = 1;
        {
            var t = new AddCommissionedEmployeeTransaction(empId, "Bob", "Home", 1000.00, 0.003);
            t.execute();
        }
        {
            var t = new SalesReceiptTransaction(empId, LocalDate.of(2001, 11, 15), 130000.00);
            t.execute();
        }

        var payDate = LocalDate.of(2001, 11, 16); // Friday
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, 1390.00);
    }

    @Test
    public void testPaySingleCommissionedEmployeeSalesReceiptSpanningPeriods() {
        int empId = 1;
        {
            var t = new AddCommissionedEmployeeTransaction(empId, "Bob", "Home", 1000.00, 0.003);
            t.execute();
        }
        {
            var t = new SalesReceiptTransaction(empId, LocalDate.of(2001, 11, 1), 79000.00);
            t.execute();
        }
        {
            var t = new SalesReceiptTransaction(empId, LocalDate.of(2001, 11, 22), 90000.00);
            t.execute();
        }

        var payDate = LocalDate.of(2001, 11, 30); // Friday
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, 1270.00);
    }

    @Test
    public void testSalariedEmployeeUnionMemberDues() {
        int empId = 1;
        int memberId = 123;
        {
            var t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home", 1000.00);
            t.execute();
        }
        {
            var t = new ChangeEmployeeMemberTransaction(empId, memberId, 19.97);
            t.execute();
        }

        var payDate = LocalDate.of(2001, 11, 30);
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, 900.15);
    }

    @Test
    public void testHourlyEmployeeUnionMemberDues() {
        int empId = 1;
        int memberId = 123;

        new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 4.75).execute();
        new ChangeEmployeeMemberTransaction(empId, memberId, 2.15).execute();
        new TimeCardTransaction(empId, LocalDate.of(2001, 11, 27), 8.0).execute();

        var payDate = LocalDate.of(2001, 11, 30);
        var pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, 35.85);
    }

    @Test
    public void testHourlyEmloyeeUnionMemberServiceCharge() {
        int empId = 1;
        int memberId = 124;

        new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 15.24).execute();
        new ChangeEmployeeMemberTransaction(empId, memberId, 9.42).execute();

        new TimeCardTransaction(empId, LocalDate.of(2001, 11, 6), 8.0).execute();

        new ServiceChargeTransaction(memberId, LocalDate.of(2001, 11, 8), 19.42).execute();

        var payDate = LocalDate.of(2001, 11, 9);
        var pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, (8 * 15.24) - (9.42 + 19.42));
    }

    @Test
    public void testServiceChargeSpanningMultiplePayPeriods() {
        int empId = 1;
        int memberId = 124;

        new AddHourlyEmployeeTransaction(empId, "Bob", "Home", 15.24).execute();
        new ChangeEmployeeMemberTransaction(empId, memberId, 9.42).execute();

        new TimeCardTransaction(empId, LocalDate.of(2001, 11, 6), 8.0).execute();

        new ServiceChargeTransaction(memberId, LocalDate.of(2001, 11, 2), 100.00).execute(); // previous Friday
        new ServiceChargeTransaction(memberId, LocalDate.of(2001, 11, 16), 200.00).execute(); // next Friday

        new ServiceChargeTransaction(memberId, LocalDate.of(2001, 11, 8), 19.42).execute();

        var payDate = LocalDate.of(2001, 11, 9);
        var pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        assertPaycheck(pc, payDate, (8 * 15.24) - (9.42 + 19.42));
    }

    private void assertPaycheck(Paycheck paycheck, LocalDate payDate, Double pay) {
        assertNotNull(paycheck);
        assertEquals(paycheck.getPayDate(), payDate);
        assertEquals(pay, paycheck.getNetPay(), 0.01);
    }

    private void assertSalariedEmployee(Employee e, Double salary) {
        SalariedClassification pc = (SalariedClassification) e.getPaymentClassification();
        assertEquals(salary, pc.getSalary());

        MonthlySchedule ps = (MonthlySchedule) e.getPaymentSchedule();
        assertNotNull(ps);

        HoldMethod pm = (HoldMethod) e.getPaymentMethod();
        assertNotNull(pm);
    }

    private void assertHourlyEmployee(Employee e, Double hourlyRate) {
        HourlyClassification pc = (HourlyClassification) e.getPaymentClassification();
        assertEquals(hourlyRate, pc.getHourlyRate());

        WeeklySchedule ps = (WeeklySchedule) e.getPaymentSchedule();
        assertNotNull(ps);

        HoldMethod pm = (HoldMethod) e.getPaymentMethod();
        assertNotNull(pm);
    }

    private void assertCommissionedEmployee(Employee e, Double salary, Double commissionRate) {
        CommissionedClassification pc = (CommissionedClassification) e.getPaymentClassification();
        assertEquals(salary, pc.getSalary());
        assertEquals(commissionRate, pc.getCommissionRate());

        BiweeklySchedule ps = (BiweeklySchedule) e.getPaymentSchedule();
        assertNotNull(ps);

        HoldMethod pm = (HoldMethod) e.getPaymentMethod();
        assertNotNull(pm);
    }
}