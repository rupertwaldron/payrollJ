package com.ruppyrup.core.payschedules;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
public abstract class PaySchedule {
    protected int paymentFrequency;
    protected LocalDateTime lastPaymentDate;

    public PaySchedule(int paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public boolean canPayEmployee(LocalDateTime date) {
        if (lastPaymentDate == null) {
            lastPaymentDate = date;
            return true;
        }
        if (Duration.between(date, lastPaymentDate).get(ChronoUnit.SECONDS) >= paymentFrequency * 3600 * 24) {
            lastPaymentDate = date;
            return true;
        }
        return false;
    }
}
