package com.craftsmanship.exercices.tram.presentation;

public class ToStringStationNameConverter implements StationNameConverter {
    @Override
    public String print(String stationName) {
        return stationName.trim();
    }
}
