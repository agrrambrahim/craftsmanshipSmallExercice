package com.craftsmanship.exercices.tram;

import com.craftsmanship.exercices.tram.presentation.StationNameConverter;
import com.craftsmanship.exercices.tram.presentation.ToStringStationNameConverter;
import com.craftsmanship.exercices.tram.presentation.TripDurationPrinter;
import com.craftsmanship.exercices.tram.models.TramLine;
import com.craftsmanship.exercices.tram.presentation.IntegerTripDurationPrinter;

public class Tram {

    public final static int DURATION_TO_HEAD_THE_NEXT_STATION = 2;
    private TramLine tripLine;
    private StationNameConverter stationNameConverter;
    private TripDurationPrinter tripDurationPrinter;

    public Tram(TramLine assignedLine) {
        this.tripLine = assignedLine;
        stationNameConverter = new ToStringStationNameConverter();
        tripDurationPrinter = new IntegerTripDurationPrinter();
    }


    public String getCurrentStation() {
        return this.tripLine.getCurrentStations(stationNameConverter);
    }

    public int pickAndRun() {
        return tripLine.runToTheNextStation(tripDurationPrinter);
    }

}
