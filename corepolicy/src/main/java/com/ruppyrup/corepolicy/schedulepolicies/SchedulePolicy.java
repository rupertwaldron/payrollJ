package com.ruppyrup.corepolicy.schedulepolicies;

public interface SchedulePolicy {

    String getType();
    int fetchPolicyData();
    void setPolicyData(int data);
}
