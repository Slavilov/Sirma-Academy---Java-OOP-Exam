package carrental.services;

import carrental.models.Car;
import carrental.models.Customer;
import carrental.models.Rental;
import carrental.models.VehicleType;
import carrental.exceptions.InvalidDataException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarFileService {
    private static final String CSV_HEADER = "Id,Make,Model,Year,Type,Status,CurrentRenterName,CurrentRenterId,StartDate,ExpectedReturnDate";

    public List<Car> readCarsFromFile(String filePath) throws IOException, InvalidDataException {
        List<Car> cars = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 6) {
                    throw new InvalidDataException("Invalid data format in CSV file");
                }

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String make = parts[1].trim();
                    String model = parts[2].trim();
                    int year = Integer.parseInt(parts[3].trim());
                    VehicleType type = VehicleType.fromString(parts[4].trim());
                    boolean isAvailable = parts[5].trim().equalsIgnoreCase("Available");

                    Car car = new Car(id, make, model, year, type);

                    if (!isAvailable && parts.length >= 10) {
                        String customerName = parts[6].trim();
                        String customerId = parts[7].trim();
                        LocalDate startDate = LocalDate.parse(parts[8].trim());
                        LocalDate expectedReturn = LocalDate.parse(parts[9].trim());

                        Customer customer = new Customer(customerName, customerId);
                        car.rentCar(customer, startDate, expectedReturn);
                    }

                    cars.add(car);
                } catch (Exception e) {
                    throw new InvalidDataException("Error parsing car data: " + e.getMessage());
                }
            }
        }
        return cars;
    }

    public void writeCarsToFile(String filePath, List<Car> cars) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(CSV_HEADER);
            writer.newLine();

            for (Car car : cars) {
                StringBuilder line = new StringBuilder();
                line.append(car.getId()).append(",");
                line.append(car.getMake()).append(",");
                line.append(car.getModel()).append(",");
                line.append(car.getYear()).append(",");
                line.append(car.getType()).append(",");

                if (car.isAvailable()) {
                    line.append("Available,,,,,");
                } else {
                    Rental rental = car.getCurrentRental();
                    line.append("Rented,");
                    line.append(rental.getCustomer().getName()).append(",");
                    line.append(rental.getCustomer().getId()).append(",");
                    line.append(rental.getStartDate()).append(",");
                    line.append(rental.getExpectedReturnDate());
                }

                writer.write(line.toString());
                writer.newLine();
            }
        }
    }
}