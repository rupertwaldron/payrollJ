package com.ruppyrup.core.schedulepolicies;


public final class SchedulePolicy {

    private SchedulePolicy() {
    }

    private static volatile int WEEKLY_SCHEDULE = 7;
    private static volatile int MONTHLY_SCHEDULE = 31;

    public static synchronized void setWeeklySchedule(int weeklySchedule) {
        WEEKLY_SCHEDULE = weeklySchedule;
    }

    public static synchronized void setMonthlySchedule(int monthlySchedule) {
        MONTHLY_SCHEDULE = monthlySchedule;
    }

    public static synchronized int getWeeklySchedule() {
        return WEEKLY_SCHEDULE;
    }

    public static synchronized int getMonthlySchedule() {
        return MONTHLY_SCHEDULE;
    }
}
