package data;

import domain.ParkingLot;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

// This class handles saving and loading ParkingLot data using serialization
public class DataPersistence {

    // Saves the ParkingLot object to a file
    public static void save(ParkingLot parkingLot, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(parkingLot);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads the ParkingLot object from a file
    public static ParkingLot load(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ParkingLot parkingLot = (ParkingLot) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return parkingLot;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
