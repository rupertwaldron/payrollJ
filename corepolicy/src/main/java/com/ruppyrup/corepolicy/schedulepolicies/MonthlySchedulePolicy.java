package com.ruppyrup.corepolicy.schedulepolicies;

import org.springframework.stereotype.Component;

@Component
public class MonthlySchedulePolicy implements SchedulePolicy {

    private int monthlySchedule = 31;

    @Override
    public int fetchPolicyData() {
        return monthlySchedule;
    }

    @Override
    public void setPolicyData(int data) {
        monthlySchedule = data;
    }
}
