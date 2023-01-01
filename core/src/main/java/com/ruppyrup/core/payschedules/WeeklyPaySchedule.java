package com.ruppyrup.core.payschedules;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.ruppyrup.core.schedulepolicies.SchedulePolicy.getWeeklySchedule;

public class WeeklyPaySchedule extends PaySchedule {

    public boolean canPayEmployee(LocalDateTime date) {
        if (lastPaymentDate == null) {
            lastPaymentDate = date;
            return true;
        }
        if (Duration.between(date, lastPaymentDate).get(ChronoUnit.SECONDS) >= (long) getWeeklySchedule()* 3600 * 24) {
            lastPaymentDate = date;
            return true;
        }
        return false;
    }
}
