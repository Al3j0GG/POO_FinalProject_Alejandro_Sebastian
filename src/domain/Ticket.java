package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Ticket {
    // PRIVATE
    private int ticketId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double totalAmount;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    
    public Ticket(int ticketId,Vehicle vehicle,ParkingSpot parkingSpot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = LocalDateTime.now();
        
    }
    // SETTERS AND GETTERS
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }
    
        
    // TotalAmountCalculate
    public double calculateTotalAmount() {
        if (exitTime != null) {
            long minutes = ChronoUnit.MINUTES.between(entryTime, exitTime);
            if (minutes == 0) minutes = 1;
            long hours = (long) Math.ceil((double) minutes / 60);     

            String type = vehicle.getVehicleType();
            if (type.equals("Car")) {
                this.totalAmount = (hours * 3600); 
            } else {
                this.totalAmount = (hours * 1800);
            }
            
            
        }
        return this.totalAmount;

    }

}