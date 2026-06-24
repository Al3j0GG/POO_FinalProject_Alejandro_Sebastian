package domain;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

// Class that manages the parking lot operations and data
public class ParkingLot implements Serializable {

    // Attributes
    private String name;
    private ArrayList<ParkingSpot> spots;
    private ArrayList<Ticket> tickets;
    private double carRate;
    private double motorcycleRate;

    // Constructor
    public ParkingLot(String name) {
        this.name = name;
        spots = new ArrayList<ParkingSpot>();
        tickets = new ArrayList<Ticket>();
        this.carRate = 4000.0;
        this.motorcycleRate = 2500.0;
    }

    // Methods
    public void addSpots(int quantity) {
        for (int i = 1; i <= quantity; i++) {
            ParkingSpot spot = new ParkingSpot(i);
            spots.add(spot);
        }
    }

    public Ticket registerEntry(Vehicle vehicle) {
        ParkingSpot availableSpot = null;

        for (ParkingSpot spot : spots) {
            if (spot.isAvailable() == true) {
                availableSpot = spot;
                break;
            }
        }

        if (availableSpot == null) {
            return null;
        }

        else {
            availableSpot.assignVehicle(vehicle);

            Ticket ticket = new Ticket(tickets.size() + 1, vehicle, availableSpot);

            tickets.add(ticket);
            return ticket;
        }
    }

    public double registerExit(int ticketId) {
        double totalAmount = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == ticketId) {
                ticket.setExitTime(LocalDateTime.now());
                ticket.getParkingSpot().removeVehicle();
                totalAmount = ticket.calculateTotalAmount(getCarRate(), getMotorcycleRate());
            }
        }
        return totalAmount;
    }

    public int getAvailableSpots() {
        int counter = 0;
        for (ParkingSpot spot : spots) {
            if (spot.isAvailable() == true) {
                counter++;
            }
        }

        return counter;
    }

    public int getTotalSpots() {
        return spots.size();
    }

    public void generateReport() {
        int totalSpots = getTotalSpots();
        int availableSpots = getAvailableSpots();
        int occupiedSpots = totalSpots - availableSpots;
        double totalIncome = 0;
        for (Ticket ticket : tickets) {
            totalIncome += ticket.getTotalAmount();
        }
    }

    // Search ticket by ID
    public Ticket searchTicket(int ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == ticketId) {
                return ticket;
            }
        }
        return null;
    }

    // Search vehicle by plate
    public Vehicle searchVehicle(String plate) {
        for (Ticket ticket : tickets) {
            if (ticket.getVehicle().getPlate().equals(plate)) {
                return ticket.getVehicle();
            }
        }
        return null;
    }

    // List all active tickets
    public ArrayList<Ticket> listTickets() {
        return tickets;
    }

    // List all vehicles
    public ArrayList<Vehicle> listVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        for (Ticket ticket : tickets) {
            vehicles.add(ticket.getVehicle());
        }
        return vehicles;
    }

    // Modify vehicle info
    public boolean modifyVehicle(String plate, String newOwnerName, String newBrand) {
        Vehicle vehicle = searchVehicle(plate);
        if (vehicle != null) {
            vehicle.setOwnerName(newOwnerName);
            vehicle.setBrand(newBrand);
            return true;
        }
        return false;
    }

    // Classification: count vehicles by type
    public int countVehiclesByType(String type) {
        int counter = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getVehicle().getVehicleType().equals(type)) {
                counter++;
            }
        }
        return counter;
    }

    // Classification: get total income by vehicle type
    public double getIncomeByType(String type) {
        double total = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getVehicle().getVehicleType().equals(type)) {
                total += ticket.getTotalAmount();
            }
        }
        return total;
    }

    // Classification: predict cost for a vehicle type based on hours
    public double predictCost(String vehicleType, int hours) {
        if (vehicleType.equals("Car")) {
            return hours * getCarRate();
        } else {
            return hours * getMotorcycleRate();
        }
    }

    // Rate configurations
    public double getCarRate() {
        if (this.carRate <= 0) {
            this.carRate = 4000.0;
        }
        return this.carRate;
    }

    public void setCarRate(double carRate) {
        this.carRate = carRate;
    }

    public double getMotorcycleRate() {
        if (this.motorcycleRate <= 0) {
            this.motorcycleRate = 2500.0;
        }
        return this.motorcycleRate;
    }

    public void setMotorcycleRate(double motorcycleRate) {
        this.motorcycleRate = motorcycleRate;
    }

    // Helper to check if a vehicle is already parked in the lot
    public boolean isVehicleParked(String plate) {
        if (plate == null) return false;
        for (Ticket ticket : tickets) {
            if (ticket.getExitTime() == null && ticket.getVehicle().getPlate().equalsIgnoreCase(plate)) {
                return true;
            }
        }
        return false;
    }

    // Serialization compatibility
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (this.carRate <= 0) {
            this.carRate = 4000.0;
        }
        if (this.motorcycleRate <= 0) {
            this.motorcycleRate = 2500.0;
        }
    }

}
