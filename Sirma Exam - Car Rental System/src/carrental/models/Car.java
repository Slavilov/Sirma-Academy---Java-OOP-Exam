package carrental.models;

import java.time.LocalDate;

public class Car {
    private int id;
    private String make;
    private String model;
    private int year;
    private VehicleType type;
    private boolean isAvailable;
    private Rental currentRental;

    public Car(int id, String make, String model, int year, VehicleType type) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
        this.isAvailable = true;
        this.currentRental = null;
    }

    public int getId() { return id; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }

    public boolean isAvailable() { return isAvailable; }

    public Rental getCurrentRental() { return currentRental; }


    public void rentCar(Customer customer, LocalDate startDate, LocalDate expectedReturn) {
        if (!isAvailable) {
            throw new IllegalStateException("Car is already rented");
        }
        this.currentRental = new Rental(this, customer, startDate, expectedReturn);
        this.isAvailable = false;
    }

    public void returnCar() {
        if (isAvailable) {
            throw new IllegalStateException("Car is not currently rented");
        }
        this.currentRental = null;
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Make: %s | Model: %s | Year: %d | Type: %s | Status: %s",
                id, make, model, year, type, isAvailable ? "Available" : "Rented by " + currentRental.getCustomer().getName());
    }
}