package com.ruppyrup.policy.controller;

import com.ruppyrup.policy.requests.PolicyRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.ruppyrup.core.schedulepolicies.SchedulePolicy.MONTHLY_SCHEDULE;
import static com.ruppyrup.core.schedulepolicies.SchedulePolicy.WEEKLY_SCHEDULE;


@RestController
public class PolicyController {

    @PutMapping("/policy/schedule")
    public void updateUnionMembership(@RequestBody PolicyRequest request) {
        WEEKLY_SCHEDULE = request.weeklySchedule();
        MONTHLY_SCHEDULE = request.monthlySchedule();
        System.out.println("Monthly schedule = " + MONTHLY_SCHEDULE);
        System.out.println("Weekly schedule = " + WEEKLY_SCHEDULE);
    }
}
