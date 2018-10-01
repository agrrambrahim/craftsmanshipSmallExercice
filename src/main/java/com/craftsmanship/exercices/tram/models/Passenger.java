package com.craftsmanship.exercices.tram.models;

public class Passenger {

    public final static String OLD_MEN = "oldMan";
    public final static String YOUNG_MEN = "youngMan";
    public final static String DISTURBER_MEN = "disturber";


    private String passengerType;
    private int delayDuration;
    private boolean willBlockTramFromRunning;
    private boolean delayDurationCounted;

    public Passenger(String passengerType, int delayDuration, boolean willBlockTramFromRunning) {
        this.passengerType = passengerType;
        this.delayDuration = delayDuration;
        this.willBlockTramFromRunning = willBlockTramFromRunning;
        this.delayDurationCounted = false;
    }

    public boolean willBlockTramFromRunning() {
        return willBlockTramFromRunning;
    }

    public int addDelayDuration(int totalTripDuration) {
        if (delayDurationCounted) {
            return totalTripDuration;
        }
        delayDurationCounted = true;
        willBlockTramFromRunning = false;
        return totalTripDuration + delayDuration;
    }
}
