package com.ruppyrup.core.models;

import com.ruppyrup.core.paymethods.PayMethod;
import com.ruppyrup.core.payschedules.PaySchedule;
import com.ruppyrup.core.paytypes.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Employee {
    private String name;
    private Long id;
    private boolean isUnionMember;
    private PayMethod payMethod;
    private PaySchedule paySchedule;
    private PayType payType;

    public Employee(String name) {
        this.name = name;
    }

    public void pay() {
        if (paySchedule.canPayEmployee(LocalDateTime.now())) {
            float amountToPay = payType.calculatePay();
            payMethod.pay(this.name, amountToPay);
        } else {
            System.out.println(this.name + " is not due to be paid now");
        }
    }
}
