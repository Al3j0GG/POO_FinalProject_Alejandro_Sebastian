package domain;

public class Motorcycle extends Vehicle{
    public Motorcycle(String brand, String plate, String ownerName){
        super(ownerName, plate, brand);

    }
    @Override
    public String getVehicleType(){
        return "Motorcycle";
    }
    
}
