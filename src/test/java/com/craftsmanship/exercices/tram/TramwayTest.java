package com.craftsmanship.exercices.tram;


import com.craftsmanship.exercices.tram.models.TramLine;
import org.junit.Assert;
import org.junit.Test;


/*
    This test is about the tramway trip, the goal is to calculate the current tram station and the trip duration.
    At the beginning the tram is affected to a tram line and is always in the default "Terminus" station.
    At each pickAndRun call, the tram will pick the passengers at the actual station, and will try to run to the next station.
    Each successful trip from one station to another will last 2 minutes.
    We have three different types of passengers:
        - youngMan: will delay the trip by 1 minute
        - oldMan: will delay the trip by 2 minutes
        - disturber: will delay the trip by 3 minute and will block the tram from running,
            the tram can only go to the next station after the second pickAndRun call, without disturbance.
 */

public class TramwayTest {

    /*
     * At the tram creation, te current station will always be the default one
     * named "Terminus".
     */
    @Test
    public void testTramShouldStartInTerminus() {
        Tram tram = new Tram(new TramLine());
        Assert.assertEquals("Terminus", tram.getCurrentStation());
    }

    /*
     * A tram line may have stations, a trip from one station to another will
     * last 2 minutes.
     */
    @Test
    public void testTripWithOneStation() {
        TramLine tramLine1 = new TramLine();
        tramLine1.addStation("station1");
        Tram tram = new Tram(tramLine1);
        Assert.assertEquals("Terminus", tram.getCurrentStation());

        int tripDuration = tram.pickAndRun();

        Assert.assertEquals(2, tripDuration);
        Assert.assertEquals("station1", tram.getCurrentStation());
    }

    /*
     * A tram line may have stations, a trip from one station to another will
     * last 2 minutes.
     */
    @Test
    public void testTripWithTwoStations() {
        TramLine tramLine1 = new TramLine();
        tramLine1.addStation("station1");
        tramLine1.addStation("station2");

        Tram tram = new Tram(tramLine1);

        int tripDuration = tram.pickAndRun();
        Assert.assertEquals(2, tripDuration);
        Assert.assertEquals("station1", tram.getCurrentStation());

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(4, tripDuration);
        Assert.assertEquals("station2", tram.getCurrentStation());
    }

    /*
     * A tram station may have passengers: - the tram will pick the passengers
     * only when it has to leave the station - youngMan is a type of passenger
     * that will delay the trip by 1 minute
     */
    @Test
    public void testTripWithAYoungPassenger() {
        TramLine tramLine1 = new TramLine();
        tramLine1.addStation("station1", "youngMan");
        tramLine1.addStation("station2");

        Tram tram = new Tram(tramLine1);

        int tripDuration = tram.pickAndRun();
        Assert.assertEquals(2, tripDuration);

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(5, tripDuration);
        Assert.assertEquals("station2", tram.getCurrentStation());
    }

    /*
     * When there are many passengers with the same type, we right the number of
     * passenger before the passenger type, for example when we have 3 youngMan
     * "3 youngMan", the default is 1.
     */
    @Test
    public void testTripWithMultiplePassengers() {
        TramLine tramLine1 = new TramLine();
        tramLine1.addStation("station1", "youngMan");
        tramLine1.addStation("station2", "2 youngMan");

        Tram tram = new Tram(tramLine1);

        int tripDuration = tram.pickAndRun();
        Assert.assertEquals(2, tripDuration);

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(5, tripDuration);
        Assert.assertEquals("station2", tram.getCurrentStation());
    }

    /*
     * oldMan is another type of passengers, he will delay the trip by 2
     * minutes.
     */
    @Test
    public void testTripWithAnOldPassenger() {
        TramLine tramLine1 = new TramLine();
        tramLine1.addStation("station1");
        tramLine1.addStation("station2", "oldMan");
        tramLine1.addStation("station3");

        Tram tram = new Tram(tramLine1);

        int tripDuration = tram.pickAndRun();
        Assert.assertEquals(2, tripDuration);

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(4, tripDuration);

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(8, tripDuration);
        Assert.assertEquals("station3", tram.getCurrentStation());
    }

    /*
     * a trip with a mix of youngMan and oldMan.
     */
    @Test
    public void testTripWithAYoungAndAnOldPassenger() {
        TramLine tramLine1 = new TramLine();
        tramLine1.addStation("station1", "youngMan");
        tramLine1.addStation("station2", "oldMan", "youngMan");
        tramLine1.addStation("station3");

        Tram tram = new Tram(tramLine1);

        int tripDuration = tram.pickAndRun();
        Assert.assertEquals(2, tripDuration);

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(5, tripDuration);

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(10, tripDuration);
        Assert.assertEquals("station3", tram.getCurrentStation());
    }

    /*
     * disturber is another type of passengers, he will block the tram from
     * leaving the station when we call pickAndRun the first time, which will
     * also delay the trip by 3 minutes, subsequent calls to pickAndRun will
     * behave normally.
     */
    @Test
    public void testTripWithADisturberPassenger() {
        TramLine tramLine1 = new TramLine();
        tramLine1.addStation("station1");
        tramLine1.addStation("station2", "disturber");
        tramLine1.addStation("station3");

        Tram tram = new Tram(tramLine1);

        int tripDuration = tram.pickAndRun();
        Assert.assertEquals(2, tripDuration);
        Assert.assertEquals("station1", tram.getCurrentStation());

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(4, tripDuration);
        Assert.assertEquals("station2", tram.getCurrentStation());

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(7, tripDuration);
        Assert.assertEquals("station2", tram.getCurrentStation());

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(9, tripDuration);
        Assert.assertEquals("station3", tram.getCurrentStation());
    }

    /*
     * the ultimate trip, when all passengers take the tram together.
     */
    @Test
    public void testTripWithAllPassengerTypes() {
        TramLine tramLine1 = new TramLine();
        tramLine1.addStation("station1", "3 youngMan");
        tramLine1.addStation("station2", "4 youngMan", "2 oldMan", "1 disturber");
        tramLine1.addStation("station3");

        Tram tram = new Tram(tramLine1);

        Assert.assertEquals(2, tram.pickAndRun());
        int tripDuration = tram.pickAndRun();
        Assert.assertEquals(7, tripDuration);
        Assert.assertEquals("station2", tram.getCurrentStation());

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(10, tripDuration);
        Assert.assertEquals("station2", tram.getCurrentStation());

        tripDuration = tram.pickAndRun();
        Assert.assertEquals(20, tripDuration);
        Assert.assertEquals("station3", tram.getCurrentStation());
    }
}
