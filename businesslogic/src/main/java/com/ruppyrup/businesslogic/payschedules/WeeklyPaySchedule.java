package com.ruppyrup.businesslogic.payschedules;

import com.ruppyrup.corepolicy.schedulepolicies.SchedulePolicy;
import com.ruppyrup.corepolicy.schedulepolicies.WeeklySchedulePolicy;
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
public class WeeklyPaySchedule extends PaySchedule {

    private final SchedulePolicy weeklySchedulePolicy;

    @Autowired
    public WeeklyPaySchedule(SchedulePolicy weeklySchedulePolicy) {
        this.weeklySchedulePolicy = weeklySchedulePolicy;
    }

    public boolean canPayEmployee(LocalDateTime date) {
        if (lastPaymentDate == null) {
            lastPaymentDate = date;
            return true;
        }
        if (Duration.between(date, lastPaymentDate).get(ChronoUnit.SECONDS) >= (long) weeklySchedulePolicy.fetchPolicyData() * 3600 * 24) {
            lastPaymentDate = date;
            return true;
        }
        return false;
    }

    @Override
    public String getType() {
        return "weekly";
    }
}
