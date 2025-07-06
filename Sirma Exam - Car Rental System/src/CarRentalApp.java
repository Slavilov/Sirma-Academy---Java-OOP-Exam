package carrental;

import carrental.models.Car;
import carrental.services.CarFileService;
import carrental.services.CarRentalService;
import carrental.services.RentalManager;
import carrental.exceptions.InvalidDataException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CarRentalApp {
    private static final String CSV_FILE = "src/data/cars.csv";

    public static void main(String[] args) {
        try {
            CarFileService fileService = new CarFileService();
            List<Car> cars = fileService.readCarsFromFile(CSV_FILE);

            CarRentalService rentalService = new CarRentalService(cars);
            RentalManager manager = new RentalManager(rentalService);

            System.out.println("Welcome to the Car Rental System!");
            manager.displayCommands();

            Scanner scanner = new Scanner(System.in);
            boolean isRunning = true;

            while (isRunning) {
                System.out.print("\nEnter command: ");
                String command = scanner.nextLine();
                isRunning = manager.execute(command);
            }

            fileService.writeCarsToFile(CSV_FILE, rentalService.getAllCars());
            System.out.println("Data saved successfully. Goodbye!");
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        } catch (InvalidDataException e) {
            System.err.println("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}