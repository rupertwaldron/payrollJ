package com.ruppyrup.businesslogic.paytypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HourlyPayType implements Hourly {
    private int weeklyHours;
    private float hourlyRate;

    @Override
    public float calculatePay() {
        return weeklyHours * hourlyRate;
    }

    @Override
    public float getPay() {
        return hourlyRate;
    }

    @Override
    public void setPay(float pay) {
        this.hourlyRate = pay;
    }
}
