package com.ruppyrup.businesslogic.payschedules;

import com.ruppyrup.corepolicy.schedulepolicies.SchedulePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MonthlyPaySchedule extends PaySchedule {


    private final SchedulePolicy monthlySchedulePolicy;

    @Autowired
    public MonthlyPaySchedule(SchedulePolicy monthlySchedulePolicy) {
        this.monthlySchedulePolicy = monthlySchedulePolicy;
    }

    public boolean canPayEmployee(LocalDateTime date) {
        if (lastPaymentDate == null) {
            lastPaymentDate = date;
            return true;
        }
        if (Duration.between(date, lastPaymentDate).get(ChronoUnit.SECONDS) >= (long) monthlySchedulePolicy.fetchPolicyData() * 3600 * 24) {
            lastPaymentDate = date;
            return true;
        }
        return false;
    }

    @Override
    public String getType() {
        return "monthly";
    }
}
