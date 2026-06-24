package domain;

import java.io.Serializable;

// Abstract class that represents a vehicle in the parking system
public abstract class Vehicle implements Serializable {

    private String ownerName;
    private String plate;
    private String brand;
    
    // Constructor
    public Vehicle(String ownerName, String plate, String brand) {
        this.ownerName = ownerName;
        this.plate = plate;
        this.brand = brand;
    }


    // Getters and Setters
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public abstract String getVehicleType();
        
    

}
