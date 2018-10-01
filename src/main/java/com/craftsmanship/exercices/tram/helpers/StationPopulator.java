package com.craftsmanship.exercices.tram.helpers;

import com.craftsmanship.exercices.tram.models.Station;
import com.craftsmanship.exercices.tram.models.Passenger;
import com.craftsmanship.exercices.tram.models.TramLine;

import java.util.ArrayList;
import java.util.List;

public class StationPopulator {

    private static final int DEFAULT_PASSENGER_NUMBER = 1;

    private static Passenger getPassengerFrom(String passengerType) {
        switch (passengerType) {
            case Passenger.OLD_MEN:
                return new Passenger(Passenger.OLD_MEN, 2, false);
            case Passenger.YOUNG_MEN:
                return new Passenger(Passenger.YOUNG_MEN, 1, false);
            case Passenger.DISTURBER_MEN:
                return new Passenger(Passenger.DISTURBER_MEN, 3, true);
            default:
                return null;
        }
    }
    public static List<Station> initiateTramLineStations() {
        List<Station> stations = new ArrayList<Station>();
        stations.add(StationPopulator.getDefaultStation());
        return stations;
    }

    private static Station getDefaultStation() {
        return new Station(TramLine.DEFAULT_STATION, new ArrayList<Passenger>());
    }

    public static List<Passenger> getPassengersFrom(String... passengers) {
        List<Passenger> passengersList = new ArrayList<Passenger>();
        for (String passenger : passengers) {
            int numberOfPassenger = getTheNumberOfPassengersFrom(passenger);
            String passengerType = getPassengerTypeFrom(passenger);
            for (int indexOfPassenger = 0; indexOfPassenger < numberOfPassenger; indexOfPassenger++)
                passengersList.add(getPassengerFrom(passengerType));
        }
        return passengersList;

    }

    private static String getPassengerTypeFrom(String passenger) {
        if (Character.isDigit(passenger.charAt(0)))
            return passenger.split(" ")[1];
        return passenger.trim();
    }

    private static int getTheNumberOfPassengersFrom(String passenger) {
        char firstCaracterFromString = passenger.charAt(0);
        if (Character.isDigit(firstCaracterFromString))
            return Integer.parseInt(String.valueOf(firstCaracterFromString));
        return DEFAULT_PASSENGER_NUMBER;
    }
}
