package com.ruppyrup.corepolicy.schedulepolicies;

import org.springframework.stereotype.Component;

@Component
public class WeeklySchedulePolicy implements SchedulePolicy {

    private int weeklySchedule = 7;

    @Override
    public String getType() {
        return "weekly";
    }

    @Override
    public int fetchPolicyData() {
        return weeklySchedule;
    }

    @Override
    public void setPolicyData(int data) {
        weeklySchedule = data;
    }
}
