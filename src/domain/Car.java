package domain;

public class Car extends Vehicle{
    public Car(String brand,String plate, String ownerName) {
        super(brand,plate,ownerName);
    }
    @Override
    public String getVehicleType() {
    return "Car";
    }
    
}

