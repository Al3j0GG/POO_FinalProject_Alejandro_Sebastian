package domain;

// Class that represents a motorcycle vehicle
public class Motorcycle extends Vehicle{
    public Motorcycle(String ownerName, String plate, String brand){
        super(ownerName, plate, brand);

    }
    
    @Override
    public String getVehicleType(){
        return "Motorcycle";
    }
    
}
