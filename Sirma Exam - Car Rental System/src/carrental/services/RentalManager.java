package carrental.services;

import carrental.models.Car;
import carrental.models.Customer;
import carrental.exceptions.CarNotFoundException;
import carrental.exceptions.RentalException;
import carrental.models.VehicleType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class RentalManager {
    private CarRentalService service;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;

    public RentalManager(CarRentalService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public boolean execute(String command) {
        try {
            String[] parts = command.split(" ", 2);
            String action = parts[0].toLowerCase();
            String params = parts.length > 1 ? parts[1] : "";

            switch (action) {
                case "add":
                    handleAddCar();
                    break;
                case "edit":
                    handleEditCar(params);
                    break;
                case "remove":
                    handleRemoveCar(params);
                    break;
                case "list":
                    handleListCars();
                    break;
                case "rent":
                    handleRentCar(params);
                    break;
                case "return":
                    handleReturnCar(params);
                    break;
                case "search":
                    handleSearch(params);
                    break;
                case "help":
                    displayCommands();
                    break;
                case "exit":
                    return false;
                default:
                    System.out.println("Unknown command. Type 'help' for available commands.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;
    }

    private void handleAddCar() {
        System.out.println("Adding a new car:");

        System.out.print("Enter ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Make: ");
        String make = scanner.nextLine();

        System.out.print("Enter Model: ");
        String model = scanner.nextLine();

        System.out.print("Enter Year: ");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Type (Sedan/Hatchback/SUV/Truck/Van): ");
        String typeStr = scanner.nextLine();
        VehicleType type = VehicleType.fromString(typeStr);

        Car car = new Car(id, make, model, year, type);
        service.addCar(car);
        System.out.println("Car added successfully!");
    }

    private void handleEditCar(String params) throws CarNotFoundException {
        int id = Integer.parseInt(params);
        Car car = service.searchById(id);

        System.out.println("Editing car: " + car);
        System.out.print("Enter new Make (current: " + car.getMake() + "): ");
        String make = scanner.nextLine();
        if (!make.isEmpty()) car.setMake(make);

        System.out.print("Enter new Model (current: " + car.getModel() + "): ");
        String model = scanner.nextLine();
        if (!model.isEmpty()) car.setModel(model);

        System.out.print("Enter new Year (current: " + car.getYear() + "): ");
        String yearStr = scanner.nextLine();
        if (!yearStr.isEmpty()) car.setYear(Integer.parseInt(yearStr));

        System.out.print("Enter new Type (current: " + car.getType() + "): ");
        String typeStr = scanner.nextLine();
        if (!typeStr.isEmpty()) car.setType(VehicleType.fromString(typeStr));

        System.out.println("Car updated successfully!");
    }

    private void handleRemoveCar(String params) throws CarNotFoundException {
        int id = Integer.parseInt(params);
        Car car = service.searchById(id);
        service.removeCar(car);
        System.out.println("Car removed successfully!");
    }

    private void handleListCars() {
        List<Car> cars = service.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("No cars in the system.");
        } else {
            System.out.println("List of all cars:");
            cars.forEach(System.out::println);
        }
    }

    private void handleRentCar(String params) throws RentalException, CarNotFoundException {
        String[] parts = params.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid format. Use: rent <carId>,<customerName>,<customerId>");
        }

        int carId = Integer.parseInt(parts[0].trim());
        String customerName = parts[1].trim();
        String customerId = parts[2].trim();

        System.out.print("Enter rental start date (yyyy-MM-dd): ");
        LocalDate startDate = parseDate(scanner.nextLine());

        System.out.print("Enter expected return date (yyyy-MM-dd): ");
        LocalDate expectedReturn = parseDate(scanner.nextLine());

        Car car = service.searchById(carId);
        Customer customer = new Customer(customerName, customerId);

        service.rentCar(car, customer, startDate, expectedReturn);
        System.out.println("Car rented successfully!");
    }

    private void handleReturnCar(String params) throws CarNotFoundException, RentalException {
        int carId = Integer.parseInt(params.trim());
        Car car = service.searchById(carId);
        service.returnCar(car);
        System.out.println("Car returned successfully!");
    }

    private void handleSearch(String params) {
        String[] parts = params.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("Invalid search format. Use: search <criteria> <value>");
            return;
        }

        String criteria = parts[0].toLowerCase();
        String value = parts[1];

        try {
            List<Car> results;
            switch (criteria) {
                case "model":
                    results = service.searchByModel(value);
                    break;
                case "make":
                    results = service.searchByMake(value);
                    break;
                case "type":
                    results = service.searchByType(value);
                    break;
                case "id":
                    Car car = service.searchById(Integer.parseInt(value));
                    results = List.of(car);
                    break;
                case "status":
                    boolean available = value.equalsIgnoreCase("available");
                    results = service.searchAvailable(available);
                    break;
                default:
                    System.out.println("Invalid search criteria. Use: model, make, type, id, or status");
                    return;
            }

            if (results.isEmpty()) {
                System.out.println("No cars found matching the criteria.");
            } else {
                System.out.println("Search results:");
                results.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error during search: " + e.getMessage());
        }
    }

    private LocalDate parseDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr, dateFormatter);
    }

    public void displayCommands() {
        System.out.println("\nAvailable commands:");
        System.out.println("add - Add a new car");
        System.out.println("edit <id> - Edit car details");
        System.out.println("remove <id> - Remove a car");
        System.out.println("list - List all cars");
        System.out.println("rent <carId>,<customerName>,<customerId> - Rent a car");
        System.out.println("return <carId> - Return a rented car");
        System.out.println("search <criteria> <value> - Search cars (criteria: model, make, type, id, status)");
        System.out.println("help - Show this help message");
        System.out.println("exit - Exit the application");
    }
}