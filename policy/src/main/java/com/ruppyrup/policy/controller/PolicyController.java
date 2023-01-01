package com.ruppyrup.policy.controller;

import com.ruppyrup.policy.requests.PolicyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.ruppyrup.core.schedulepolicies.SchedulePolicy.*;


@RestController
public class PolicyController {

    @GetMapping("/policy/schedule")
    public ResponseEntity<PolicyRequest> getSchedulePolicy() {
        PolicyRequest policyRequest = new PolicyRequest(getMonthlySchedule(), getWeeklySchedule());
        return ResponseEntity.ok(policyRequest);
    }


    @PutMapping("/policy/schedule")
    public void updateUnionMembership(@RequestBody PolicyRequest request) {
        setWeeklySchedule(request.weeklySchedule());
        setMonthlySchedule(request.monthlySchedule());
        System.out.println("Monthly schedule = " + getMonthlySchedule());
        System.out.println("Weekly schedule = " + getWeeklySchedule());
    }
}
