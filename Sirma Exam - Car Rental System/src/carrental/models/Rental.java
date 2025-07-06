package carrental.models;

import java.time.LocalDate;

public class Rental {
    private Car car;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;

    public Rental(Car car, Customer customer, LocalDate startDate, LocalDate expectedReturnDate) {
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.expectedReturnDate = expectedReturnDate;
    }

    public Car getCar() { return car; }
    public Customer getCustomer() { return customer; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getExpectedReturnDate() { return expectedReturnDate; }
    public LocalDate getActualReturnDate() { return actualReturnDate; }

    public void completeRental(LocalDate returnDate) {
        this.actualReturnDate = returnDate;
    }

    @Override
    public String toString() {
        return String.format("Rental: %s by %s from %s to %s",
                car.getModel(), customer.getName(), startDate,
                actualReturnDate != null ? actualReturnDate : expectedReturnDate);
    }
}