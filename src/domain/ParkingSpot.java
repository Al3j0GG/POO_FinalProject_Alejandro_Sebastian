package domain;

public class ParkingSpot {
    private int spotNumber;
    private boolean occupied;
    private Vehicle vehicle;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.occupied = false;
    }

    public boolean isAvaible() {
        return !occupied;
    }
    public void assingVehicle(Vehicle vehicle){
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
