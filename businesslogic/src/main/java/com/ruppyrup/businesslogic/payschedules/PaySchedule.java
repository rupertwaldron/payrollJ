package com.ruppyrup.businesslogic.payschedules;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public abstract class PaySchedule {

    protected LocalDateTime lastPaymentDate;
    protected PaySchedule() {
        this.lastPaymentDate = null;
    }

    public abstract boolean canPayEmployee(LocalDateTime date);

    public abstract String getType();
}
