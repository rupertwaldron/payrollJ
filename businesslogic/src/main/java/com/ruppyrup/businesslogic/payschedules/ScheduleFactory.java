package com.ruppyrup.businesslogic.payschedules;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

@Service
public class ScheduleFactory {

    public PaySchedule createPayScheduleOfType(String type) {
        return switch (type) {
            case "monthly" -> getMonthlyPaySchedule();
            case "weekly" -> getWeeklyPaySchedule();
            default -> throw new RuntimeException("Can't find the correct schedule bean");
        };
    }

    @Lookup
    public MonthlyPaySchedule getMonthlyPaySchedule() {
        return null;
    }

    @Lookup
    public WeeklyPaySchedule getWeeklyPaySchedule() {
        return null;
    }

}
