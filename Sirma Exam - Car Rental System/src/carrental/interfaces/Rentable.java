package carrental.interfaces;

import carrental.models.Car;
import carrental.models.Customer;
import java.time.LocalDate;

public interface Rentable {
    void rentCar(Car car, Customer customer, LocalDate startDate, LocalDate expectedReturn);
    void returnCar(Car car);
}