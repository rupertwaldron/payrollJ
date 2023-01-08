package com.ruppyrup.businesslogic.paytypes;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalaryPayType implements PayType {

    private float salary;

    @Override
    public float calculatePay() {
        return salary / 12;
    }

    @Override
    public float getPay() {
        return salary;
    }

    @Override
    public void setPay(float pay) {
        this.salary = pay;
    }


//    @Override
//    public int getWeeklyHours() {
//        //ToDo ISP violation
//        return -1;
//    }
//
//    @Override
//    public void setWeeklyHours(int weeklyHours) {
//        //ToDo ISP violation
//    }
}
