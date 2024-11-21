package com.capgemini.wsb.fitnesstracker.dates;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class DatesUtils {
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
