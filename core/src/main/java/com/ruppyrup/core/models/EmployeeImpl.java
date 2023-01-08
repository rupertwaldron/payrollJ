package com.ruppyrup.core.models;


import com.ruppyrup.businesslogic.Factory;
import com.ruppyrup.businesslogic.paymethods.BankPayMethod;
import com.ruppyrup.businesslogic.paymethods.PayMethod;
import com.ruppyrup.businesslogic.payschedules.PaySchedule;
import com.ruppyrup.businesslogic.paytypes.*;
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

    private Factory<PaySchedule> scheduleFactory;

    private Factory<PayType> payTypeFactory;

    private String name;
    private Long id;
    private boolean isUnionMember;
    private PayMethod payMethod;
    private PaySchedule paySchedule;
    private PayType payType;

    public EmployeeImpl(String name) {
        this.name = name;
    }

    @Autowired
    public EmployeeImpl(Factory<PaySchedule> scheduleFactory, Factory<PayType> payTypeFactory) {
        this.scheduleFactory = scheduleFactory;
        this.payTypeFactory = payTypeFactory;
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
    public String getEmployeeInfo() {
        String a = """
                {
                       "name" : %s,
                       "paySchedule" : %s,
                       "payType" : %s,
                       "paymentDetails" : %s,
                       "unionMember" : %b
                }
                """;
        return String.format(a,
                name,
                paySchedule.getClass().getSimpleName(),
                payType.payTypeDetails(),
                payMethod.getPaymentDetails(),
                isUnionMember
                );
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
    public void setPayMethod(String accountNumber) {
        payMethod = new BankPayMethod(accountNumber);
    }

    @Override
    public void setPaySchedule(String type) {
        paySchedule = scheduleFactory.retreive(type);
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

    @Override
    public void setPayType(String payType, float pay) {
        this.payType = payTypeFactory.retreive(payType);
        this.payType.setPay(pay);
    }
}
