package com.lgcoding.payroll.classifications;

import com.lgcoding.payroll.domain.Paycheck;
import com.lgcoding.payroll.domain.PaymentClassification;
import com.lgcoding.util.DateUtil;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
public class HourlyClassification implements PaymentClassification {

    private final Double hourlyRate;
    private final Map<String, TimeCard> timeCards = new HashMap<String, TimeCard>();

    public HourlyClassification(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public TimeCard addTimeCard(LocalDate date, double hours) {
        var tc = new TimeCard(date, hours);
        timeCards.put(date.toString(), tc);
        return tc;
    }

    public TimeCard getTimeCard(LocalDate date) {
        return timeCards.get(date.toString());
    }

    @Override
    public Double calculatePay(Paycheck paycheck) {
        return timeCards.values().stream().filter(tc -> DateUtil.isBetween(tc.getDate(), paycheck.getStartDate(),
                        paycheck.getPayDate()))
                .map(tc -> calculatePayForTimeCard(tc)).reduce(0.0, (a, b) -> a + b);
    }

    private Double calculatePayForTimeCard(TimeCard timecard) {
        if (DateUtil.isWeekend(timecard.getDate())) {
            return calculatePayForWeekendTimeCard(timecard);
        }
        return calculatePayForWeekdayTimeCard(timecard);
    }

    private Double calculatePayForWeekdayTimeCard(TimeCard timecard) {
        Double STRAIGHT_TIME_HOURS = 8.0;
        Double OVERTIME_RATE = 1.5;
        Double overtime = Math.max(0.0, timecard.getHours() - STRAIGHT_TIME_HOURS);
        Double straightTime = timecard.getHours() - overtime;
        return straightTime * hourlyRate + overtime * hourlyRate * OVERTIME_RATE;
    }

    private Double calculatePayForWeekendTimeCard(TimeCard timecard) {
        Double OVERTIME_RATE = 1.5;
        return timecard.getHours() * OVERTIME_RATE * hourlyRate;
    }
}