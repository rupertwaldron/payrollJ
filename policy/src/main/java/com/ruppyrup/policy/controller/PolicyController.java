package com.ruppyrup.policy.controller;

import com.ruppyrup.corepolicy.schedulepolicies.SchedulePolicy;
import com.ruppyrup.policy.requests.PolicyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PolicyController {

    private final SchedulePolicy monthlySchedulePolicy;
    private final SchedulePolicy weeklySchedulePolicy;

    public PolicyController(SchedulePolicy monthlySchedulePolicy, SchedulePolicy weeklySchedulePolicy) {
        this.monthlySchedulePolicy = monthlySchedulePolicy;
        this.weeklySchedulePolicy = weeklySchedulePolicy;
    }

    @GetMapping("/policy/schedule")
    public ResponseEntity<PolicyRequest> getSchedulePolicy() {
        PolicyRequest policyRequest = new PolicyRequest(monthlySchedulePolicy.fetchPolicyData(), weeklySchedulePolicy.fetchPolicyData());
        return ResponseEntity.ok(policyRequest);
    }


    @PutMapping("/policy/schedule")
    public void updateSchedulePolicy(@RequestBody PolicyRequest request) {
        weeklySchedulePolicy.setPolicyData(request.weeklySchedule());
        monthlySchedulePolicy.setPolicyData(request.monthlySchedule());
        System.out.println("Monthly schedule = " + monthlySchedulePolicy.fetchPolicyData());
        System.out.println("Weekly schedule = " + weeklySchedulePolicy.fetchPolicyData());
    }
}
