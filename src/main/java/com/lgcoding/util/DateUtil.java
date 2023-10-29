package com.lgcoding.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {
    public static boolean isBetween(LocalDate date, LocalDate start, LocalDate end) {
        return !date.isBefore(start) && !date.isAfter(end);
    }

    public static boolean isInBiweekly(LocalDate date) {
        var lastDay = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
        var biweeklydays = new ArrayList<Integer>();

        for (var i = 1; i <= date.getMonth().maxLength();) {
            biweeklydays.add(i);

            if (lastDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
                i = i + 8;
            } else {
                i += 1;
            }

            lastDay = lastDay.plusDays(1);
        }

        return biweeklydays.contains(date.getDayOfMonth());
    }

    public static boolean isWeekend(LocalDate date) {
        var day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}