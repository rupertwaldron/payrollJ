package com.ruppyrup.core.models;


import com.ruppyrup.businesslogic.paymethods.BankPayMethod;
import com.ruppyrup.businesslogic.paymethods.PayMethod;
import com.ruppyrup.businesslogic.payschedules.PaySchedule;
import com.ruppyrup.businesslogic.payschedules.ScheduleFactory;
import com.ruppyrup.businesslogic.paytypes.Hourly;
import com.ruppyrup.businesslogic.paytypes.HourlyPayType;
import com.ruppyrup.businesslogic.paytypes.PayType;
import com.ruppyrup.businesslogic.paytypes.SalaryPayType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EmployeeImpl implements Employee {

    @Autowired
    private ScheduleFactory scheduleFactory;

    private String name;
    private Long id;
    private boolean isUnionMember;
    private PayMethod payMethod;
    private PaySchedule paySchedule;
    private PayType payType;

    public EmployeeImpl(String name) {
        this.name = name;
    }

    @Override
    public void pay() {
        if (paySchedule.canPayEmployee(LocalDateTime.now())) {
            float amountToPay = payType.calculatePay();
            payMethod.pay(this.name, amountToPay);
        } else {
            System.out.println(this.name + " is not due to be paid now");
        }
    }

    @Override
    public float getPay() {
        return payType.getPay();
    }

    @Override
    public String getPayMethod() {
        return payMethod.getClass().getSimpleName();
    }

    @Override
    public String getPaySchedule() {
        return paySchedule.getClass().getSimpleName();
    }

    @Override
    public String getPayType() {
        return payType.getClass().getSimpleName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPaymentDetails() {
        return payMethod.getPaymentDetails();
    }

    @Override
    public int getWeeklyHours() {
        try {
            return ((Hourly)payType).getWeeklyHours();
        } catch (ClassCastException cce) {
            return -1;
        }
    }

    @Override
    public String getLastPaymentInstruction() {
        return payMethod.getLastInstruction();
    }

    @Override
    public boolean isUnionMember() {
        return isUnionMember;
    }

    @Override
    public void setBankPayMethod(String accountNumber) {
        payMethod = new BankPayMethod(accountNumber);
    }

    @Override
    public void setSalaryPayType(float salary) {
        payType = new SalaryPayType(salary);
    }

    @Override
    public void setHourlyPayType(float hourlyRate) {
        payType = new HourlyPayType(hourlyRate);
    }

    @Override
    public void setPaySchedule(String type) {
        paySchedule = scheduleFactory.createPayScheduleOfType(type);
    }

    @Override
    public void setWeeklyHours(int weeklyHours) {
        try {
            ((Hourly)payType).setWeeklyHours(weeklyHours);
        } catch (ClassCastException cce) {
            throw new RuntimeException("You can't set the hours for this type of employee");
        }
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setUnionMember(boolean hasSignedUp) {
        isUnionMember = hasSignedUp;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
