package com.ruppyrup.businesslogic.payschedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ScheduleFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public PaySchedule createPayScheduleOfType(String type) {
        return switch (type) {
            case "monthly" -> applicationContext.getBean(MonthlyPaySchedule.class);
            case "weekly" -> applicationContext.getBean(WeeklyPaySchedule.class);
            default -> throw new RuntimeException("Can't find the correct schedule bean");
        };
    }

}
