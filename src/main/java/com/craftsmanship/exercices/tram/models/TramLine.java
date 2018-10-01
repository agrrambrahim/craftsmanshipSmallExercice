package com.craftsmanship.exercices.tram.models;

import com.craftsmanship.exercices.tram.helpers.StationPopulator;
import com.craftsmanship.exercices.tram.presentation.StationNameConverter;
import com.craftsmanship.exercices.tram.presentation.TripDurationPrinter;

import java.util.List;

public class TramLine {

    public static final String DEFAULT_STATION = "Terminus";


    private List<Station> stations;
    private int currentStationIndex;
    private int tripDuration;

    public TramLine() {
        this.stations=StationPopulator.initiateTramLineStations();
        this.currentStationIndex = 0;
        this.tripDuration = 0;
    }

    public void addStation(String stationName, String... passengers) {
        Station station = new Station(stationName, StationPopulator.getPassengersFrom(passengers));
        this.stations.add(station);
    }

    public String getCurrentStations(StationNameConverter stationNameConverter) {
        return stations.get(currentStationIndex).printStationName(stationNameConverter);
    }

    public int runToTheNextStation(TripDurationPrinter tripDurationPrinter) {
        Station currentStation = stations.get(currentStationIndex);
        if (currentStation.isTramGoingToTheNextStation()) {
            goToTheNextStation();
        }
        return tripDuration = currentStation.calculateTripDuration(tripDurationPrinter, tripDuration);
    }

    private void goToTheNextStation() {
        currentStationIndex++;
    }
}
