package domain;

import java.io.Serializable;

// Class that represents a parking spot in the parking lot
public class ParkingSpot implements Serializable {
    private int spotNumber;
    private boolean occupied;
    private Vehicle vehicle;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.occupied = false;
    }

    public boolean isAvailable() {
        return !occupied;
    }

    public void assignVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
        this.occupied = true;


    }

    public void removeVehicle(){
        this.occupied = false;
        this.vehicle= null;
    }

    public int getSpotNumber(){
        return spotNumber;
    }

}
