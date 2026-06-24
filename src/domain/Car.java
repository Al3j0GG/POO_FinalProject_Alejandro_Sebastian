package domain;

// Class that represents a car vehicle
public class Car extends Vehicle{
    public Car(String ownerName, String plate, String brand) {
        super(ownerName, plate ,brand);
    }

    @Override
    public String getVehicleType() {
    return "Car";
    }
    
}

