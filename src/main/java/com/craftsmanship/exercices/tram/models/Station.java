package com.craftsmanship.exercices.tram.models;

import com.craftsmanship.exercices.tram.Tram;
import com.craftsmanship.exercices.tram.presentation.StationNameConverter;
import com.craftsmanship.exercices.tram.presentation.TripDurationPrinter;

import java.util.List;
import java.util.Optional;

public class Station {

    private List<Passenger> passengers;
    private String stationName;

    public Station(String stationName, List<Passenger> passengers) {
        this.passengers = passengers;
        this.stationName = stationName;
    }

    public String printStationName(StationNameConverter stationNameConverter) {
        return stationNameConverter.print(stationName);
    }

    public boolean isTramGoingToTheNextStation() {
        return !containsBlockerPassengers();
    }

    private boolean containsBlockerPassengers() {
        return this.getBlockerPassenger().isPresent();
    }

    private Optional<Passenger> getBlockerPassenger() {
        Optional<Passenger> blockerPassengers = passengers.stream()
                .filter(Passenger::willBlockTramFromRunning)
                .findFirst();
        return blockerPassengers;
    }

    public int calculateTripDuration(TripDurationPrinter tripDurationPrinter, int tripDuration) {
        if (this.isEmptyStation()) {
            return calculateTripDurationForEmptyStation(tripDurationPrinter, tripDuration);
        }
        return calculateTripDurationForFullStation(tripDurationPrinter, tripDuration);
    }

    private boolean isEmptyStation() {
        return passengers.size() == 0;
    }

    private int calculateTripDurationForEmptyStation(TripDurationPrinter tripDurationPrinter, int totalTripDuration) {
        return tripDurationPrinter.print(totalTripDuration + Tram.DURATION_TO_HEAD_THE_NEXT_STATION);
    }

    private int calculateTripDurationForFullStation(TripDurationPrinter tripDurationPrinter, int totalTripDuration) {
        if (isTramGoingToTheNextStation()) {
            return CalculateTripForDepartingTram(tripDurationPrinter, totalTripDuration);
        }
        return CalculateTripDurationForBlockedTram(tripDurationPrinter, totalTripDuration);
    }

    private int CalculateTripForDepartingTram(TripDurationPrinter tripDurationPrinter, int totalTripDuration) {
        for (Passenger passenger : passengers)
            totalTripDuration = passenger.addDelayDuration(totalTripDuration);
        return tripDurationPrinter.print(totalTripDuration + Tram.DURATION_TO_HEAD_THE_NEXT_STATION);
    }

    private int CalculateTripDurationForBlockedTram(TripDurationPrinter tripDurationPrinter, int totalTripDuration) {
        totalTripDuration = getBlockerPassenger().get().addDelayDuration(totalTripDuration);
        return tripDurationPrinter.print(totalTripDuration);
    }
}
