package com.ruppyrup.core.paytypes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HourlyPayType implements PayType {
    private int weeklyHours;
    private float hourlyRate;

    public HourlyPayType(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public float calculatePay() {
        return weeklyHours * hourlyRate;
    }
}
