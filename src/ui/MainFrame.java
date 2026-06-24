package ui;

import domain.ParkingLot;
import domain.Car;
import domain.Motorcycle;
import domain.Ticket;
import domain.Vehicle;
import data.DataPersistence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Main window of the parking system application
public class MainFrame extends JFrame {

    private ParkingLot parkingLot;
    private static final String DATA_FILE = "src/data/parking_data.ser";

    // Panels
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Register Entry components
    private JTextField txtOwnerName;
    private JTextField txtPlate;
    private JTextField txtBrand;
    private JComboBox<String> cmbVehicleType;
    private JTextArea txtEntryResult;

    // Register Exit components
    private JTextField txtExitTicketId;
    private JTextArea txtExitResult;

    // Search components
    private JTextField txtSearchPlate;
    private JTextArea txtSearchResult;

    // Report components
    private JTextArea txtReport;
    private JTextArea txtReportArea;

    // Classification components
    private JTextArea txtClassification;

    // Prediction components
    private JTextField txtPredictHours;
    private JComboBox<String> cmbPredictType;
    private JTextArea txtPredictResult;

    // Constructor
    public MainFrame() {
        loadData();
        if (parkingLot == null) {
            parkingLot = new ParkingLot("Main Parking");
            parkingLot.addSpots(50);
        }
        initUI();
    }

    // Initialize the user interface
    private void initUI() {
        setTitle("Smart Parking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Main panel with card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create panels
        mainPanel.add(createMenuPanel(), "menu");
        mainPanel.add(createEntryPanel(), "entry");
        mainPanel.add(createExitPanel(), "exit");
        mainPanel.add(createSearchPanel(), "search");
        mainPanel.add(createListPanel(), "list");
        mainPanel.add(createReportPanel(), "report");
        mainPanel.add(createClassificationPanel(), "classification");
        mainPanel.add(createPredictionPanel(), "prediction");

        add(mainPanel);
        cardLayout.show(mainPanel, "menu");
    }

    // Create the main menu panel
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("SMART PARKING SYSTEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        JButton btnEntry = new JButton("Register Entry");
        JButton btnExit = new JButton("Register Exit");
        JButton btnSearch = new JButton("Search Vehicle");
        JButton btnList = new JButton("List Vehicles");
        JButton btnReport = new JButton("Generate Report");
        JButton btnClassification = new JButton("Classification");
        JButton btnPrediction = new JButton("Prediction");
        JButton btnConfigureRates = new JButton("Configure Rates");

        btnEntry.addActionListener(e -> cardLayout.show(mainPanel, "entry"));
        btnExit.addActionListener(e -> cardLayout.show(mainPanel, "exit"));
        btnSearch.addActionListener(e -> cardLayout.show(mainPanel, "search"));
        btnList.addActionListener(e -> { listVehicles(); cardLayout.show(mainPanel, "list"); });
        btnReport.addActionListener(e -> { showReport(); cardLayout.show(mainPanel, "report"); });
        btnClassification.addActionListener(e -> { showClassification(); cardLayout.show(mainPanel, "classification"); });
        btnPrediction.addActionListener(e -> cardLayout.show(mainPanel, "prediction"));
        btnConfigureRates.addActionListener(e -> showConfigureRatesDialog());

        buttonPanel.add(btnEntry);
        buttonPanel.add(btnExit);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnList);
        buttonPanel.add(btnReport);
        buttonPanel.add(btnClassification);
        buttonPanel.add(btnPrediction);
        buttonPanel.add(btnConfigureRates);

        panel.add(buttonPanel, BorderLayout.CENTER);

        JButton btnExitApp = new JButton("Exit");
        btnExitApp.addActionListener(e -> {
            saveData();
            System.exit(0);
        });
        panel.add(btnExitApp, BorderLayout.SOUTH);

        return panel;
    }

    // Create the register entry panel
    private JPanel createEntryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("Owner Name:"));
        txtOwnerName = new JTextField();
        formPanel.add(txtOwnerName);

        formPanel.add(new JLabel("Plate:"));
        txtPlate = new JTextField();
        formPanel.add(txtPlate);

        formPanel.add(new JLabel("Brand:"));
        txtBrand = new JTextField();
        formPanel.add(txtBrand);

        formPanel.add(new JLabel("Vehicle Type:"));
        cmbVehicleType = new JComboBox<>(new String[]{"Car", "Motorcycle"});
        formPanel.add(cmbVehicleType);

        JButton btnRegister = new JButton("Register");
        JButton btnBack = new JButton("Back");

        btnRegister.addActionListener(e -> registerEntry());
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);

        txtEntryResult = new JTextArea(5, 40);
        txtEntryResult.setEditable(false);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtEntryResult), BorderLayout.SOUTH);

        return panel;
    }

    // Create the register exit panel
    private JPanel createExitPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("Ticket ID:"));
        txtExitTicketId = new JTextField();
        formPanel.add(txtExitTicketId);

        JButton btnProcess = new JButton("Process Exit");
        JButton btnBack = new JButton("Back");

        btnProcess.addActionListener(e -> registerExit());
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnProcess);
        buttonPanel.add(btnBack);

        txtExitResult = new JTextArea(5, 40);
        txtExitResult.setEditable(false);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtExitResult), BorderLayout.SOUTH);

        return panel;
    }

    // Create the search panel
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("Plate:"));
        txtSearchPlate = new JTextField();
        formPanel.add(txtSearchPlate);

        JButton btnSearch = new JButton("Search");
        JButton btnBack = new JButton("Back");

        btnSearch.addActionListener(e -> searchVehicle());
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnBack);

        txtSearchResult = new JTextArea(5, 40);
        txtSearchResult.setEditable(false);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtSearchResult), BorderLayout.SOUTH);

        return panel;
    }

    // Create the list panel
    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtReport = new JTextArea(15, 40);
        txtReport.setEditable(false);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        panel.add(new JScrollPane(txtReport), BorderLayout.CENTER);
        panel.add(btnBack, BorderLayout.SOUTH);

        return panel;
    }

    // Create the report panel
    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtReportArea = new JTextArea(15, 40);
        txtReportArea.setEditable(false);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        panel.add(new JScrollPane(txtReportArea), BorderLayout.CENTER);
        panel.add(btnBack, BorderLayout.SOUTH);

        return panel;
    }

    // Create the classification panel
    private JPanel createClassificationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtClassification = new JTextArea(15, 40);
        txtClassification.setEditable(false);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        panel.add(new JScrollPane(txtClassification), BorderLayout.CENTER);
        panel.add(btnBack, BorderLayout.SOUTH);

        return panel;
    }

    // Create the prediction panel
    private JPanel createPredictionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.add(new JLabel("Vehicle Type:"));
        cmbPredictType = new JComboBox<>(new String[]{"Car", "Motorcycle"});
        formPanel.add(cmbPredictType);

        formPanel.add(new JLabel("Estimated Hours:"));
        txtPredictHours = new JTextField();
        formPanel.add(txtPredictHours);

        JButton btnPredict = new JButton("Predict Cost");
        JButton btnBack = new JButton("Back");

        btnPredict.addActionListener(e -> predictCost());
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnPredict);
        buttonPanel.add(btnBack);

        txtPredictResult = new JTextArea(5, 40);
        txtPredictResult.setEditable(false);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtPredictResult), BorderLayout.SOUTH);

        return panel;
    }

    // Show classification of vehicles by type
    private void showClassification() {
        int cars = parkingLot.countVehiclesByType("Car");
        int motorcycles = parkingLot.countVehiclesByType("Motorcycle");
        double carIncome = parkingLot.getIncomeByType("Car");
        double motorcycleIncome = parkingLot.getIncomeByType("Motorcycle");

        txtClassification.setText("=== VEHICLE CLASSIFICATION ===\n"
                + "Cars: " + cars + "\n"
                + "Motorcycles: " + motorcycles + "\n"
                + "Total: " + (cars + motorcycles) + "\n\n"
                + "=== INCOME BY TYPE ===\n"
                + "Cars income: $" + carIncome + "\n"
                + "Motorcycles income: $" + motorcycleIncome + "\n"
                + "Total income: $" + (carIncome + motorcycleIncome));
    }

    // Predict cost based on vehicle type and hours
    private void predictCost() {
        try {
            int hours = Integer.parseInt(txtPredictHours.getText());
            String type = (String) cmbPredictType.getSelectedItem();
            double cost = parkingLot.predictCost(type, hours);
            txtPredictResult.setText("Prediction for " + type + "\n"
                    + "Estimated hours: " + hours + "\n"
                    + "Cost per hour: $" + (type.equals("Car") ? parkingLot.getCarRate() : parkingLot.getMotorcycleRate()) + "\n"
                    + "Total predicted cost: $" + cost);
        } catch (NumberFormatException e) {
            txtPredictResult.setText("Please enter a valid number of hours");
        }
    }

    // Register a vehicle entry
    private void registerEntry() {
        String ownerName = txtOwnerName.getText();
        String plate = txtPlate.getText();
        String brand = txtBrand.getText();
        String type = (String) cmbVehicleType.getSelectedItem();

        if (ownerName.isEmpty() || plate.isEmpty() || brand.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        if (parkingLot.isVehicleParked(plate)) {
            JOptionPane.showMessageDialog(this, "Vehicle with plate " + plate + " is already in the parking lot.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            txtEntryResult.setText("Error: Vehicle with plate " + plate + " is already registered and parked.");
            return;
        }

        Vehicle vehicle;
        if (type.equals("Car")) {
            vehicle = new Car(ownerName, plate, brand);
        } else {
            vehicle = new Motorcycle(ownerName, plate, brand);
        }

        Ticket ticket = parkingLot.registerEntry(vehicle);
        if (ticket != null) {
            txtEntryResult.setText("Entry registered successfully\nTicket ID: " + ticket.getTicketId()
                    + "\nSpot: " + ticket.getParkingSpot().getSpotNumber());
            saveData();
        } else {
            txtEntryResult.setText("No available spots");
        }
    }

    // Register a vehicle exit
    private void registerExit() {
        try {
            int ticketId = Integer.parseInt(txtExitTicketId.getText());
            double amount = parkingLot.registerExit(ticketId);
            if (amount > 0) {
                txtExitResult.setText("Exit processed\nAmount to pay: $" + amount);
                saveData();
            } else {
                txtExitResult.setText("Ticket not found");
            }
        } catch (NumberFormatException e) {
            txtExitResult.setText("Invalid ticket ID");
        }
    }

    // Search a vehicle by plate
    private void searchVehicle() {
        String plate = txtSearchPlate.getText();
        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a plate");
            return;
        }

        Vehicle vehicle = parkingLot.searchVehicle(plate);
        if (vehicle != null) {
            txtSearchResult.setText("Vehicle found\nOwner: " + vehicle.getOwnerName()
                    + "\nPlate: " + vehicle.getPlate()
                    + "\nBrand: " + vehicle.getBrand()
                    + "\nType: " + vehicle.getVehicleType());
        } else {
            txtSearchResult.setText("Vehicle not found");
        }
    }

    // List all vehicles in the parking lot
    private void listVehicles() {
        ArrayList<Ticket> tickets = parkingLot.listTickets();
        if (tickets.isEmpty()) {
            txtReport.setText("No vehicles registered");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("=== LIST OF VEHICLES AND TICKETS ===\n\n");
            for (Ticket t : tickets) {
                Vehicle v = t.getVehicle();
                sb.append("Ticket ID: ").append(t.getTicketId())
                  .append(" | Plate: ").append(v.getPlate())
                  .append(" | Owner: ").append(v.getOwnerName())
                  .append(" | Brand: ").append(v.getBrand())
                  .append(" | Type: ").append(v.getVehicleType())
                  .append(" | Spot: ").append(t.getParkingSpot().getSpotNumber());
                
                if (t.getExitTime() == null) {
                    sb.append(" | Status: PARKED");
                } else {
                    sb.append(" | Status: EXITED | Paid: $").append(t.getTotalAmount());
                }
                sb.append("\n");
            }
            txtReport.setText(sb.toString());
        }
    }

    // Show parking report
    private void showReport() {
        int totalSpots = parkingLot.getTotalSpots();
        int availableSpots = parkingLot.getAvailableSpots();
        int occupiedSpots = totalSpots - availableSpots;
        txtReportArea.setText("Total spots: " + totalSpots
                + "\nAvailable: " + availableSpots
                + "\nOccupied: " + occupiedSpots);
    }

    // Save data to file
    private void saveData() {
        DataPersistence.save(parkingLot, DATA_FILE);
    }

    // Load data from file
    private void loadData() {
        parkingLot = DataPersistence.load(DATA_FILE);
    }

    // Opens settings dialog to configure car and motorcycle hourly rates
    private void showConfigureRatesDialog() {
        JDialog dialog = new JDialog(this, "Configure Rates", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Car Rate ($/hour):"));
        JTextField txtCarRate = new JTextField(String.valueOf(parkingLot.getCarRate()));
        formPanel.add(txtCarRate);

        formPanel.add(new JLabel("Motorcycle Rate ($/hour):"));
        JTextField txtMotorcycleRate = new JTextField(String.valueOf(parkingLot.getMotorcycleRate()));
        formPanel.add(txtMotorcycleRate);

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        btnSave.addActionListener(e -> {
            try {
                double carRate = Double.parseDouble(txtCarRate.getText().trim());
                double motorcycleRate = Double.parseDouble(txtMotorcycleRate.getText().trim());

                if (carRate <= 0 || motorcycleRate <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Rates must be positive numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                parkingLot.setCarRate(carRate);
                parkingLot.setMotorcycleRate(motorcycleRate);
                saveData();
                JOptionPane.showMessageDialog(dialog, "Rates updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numeric values for rates.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // Main method - entry point of the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
