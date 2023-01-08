package com.ruppyrup.businesslogic.paytypes;

import com.ruppyrup.businesslogic.Factory;
import com.ruppyrup.businesslogic.payschedules.MonthlyPaySchedule;
import com.ruppyrup.businesslogic.payschedules.PaySchedule;
import com.ruppyrup.businesslogic.payschedules.WeeklyPaySchedule;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

@Service
public class PayTypeFactory implements Factory<PayType> {

    public PayType retreive(String type) {
        return switch (type) {
            case "salary" -> getSalaryPayType();
            case "hourlyRate" -> getHourlyPayType();
            default -> throw new RuntimeException("Can't find the correct schedule bean");
        };
    }

    @Lookup
    public HourlyPayType getHourlyPayType() {
        return null;
    }

    @Lookup
    public SalaryPayType getSalaryPayType() {
        return null;
    }

}
