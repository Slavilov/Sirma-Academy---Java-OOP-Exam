package carrental.services;

import carrental.interfaces.Rentable;
import carrental.interfaces.Searchable;
import carrental.models.Car;
import carrental.models.Customer;
import carrental.exceptions.CarNotFoundException;
import carrental.exceptions.RentalException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarRentalService implements Rentable, Searchable {
    private List<Car> cars;

    public CarRentalService(List<Car> cars) {
        this.cars = new ArrayList<>(cars);
    }

    @Override
    public void rentCar(Car car, Customer customer, LocalDate startDate, LocalDate expectedReturn) throws RentalException {
        if (!cars.contains(car)) {
            throw new RentalException("Car not found in the system");
        }
        car.rentCar(customer, startDate, expectedReturn);
    }

    @Override
    public void returnCar(Car car) throws RentalException {
        if (!cars.contains(car)) {
            throw new RentalException("Car not found in the system");
        }
        car.returnCar();
    }

    @Override
    public List<Car> searchByModel(String model) {
        return cars.stream()
                .filter(c -> c.getModel().equalsIgnoreCase(model))
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> searchByMake(String make) {
        return cars.stream()
                .filter(c -> c.getMake().equalsIgnoreCase(make))
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> searchByType(String type) {
        return cars.stream()
                .filter(c -> c.getType().toString().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> searchAvailable(boolean available) {
        return cars.stream()
                .filter(c -> c.isAvailable() == available)
                .collect(Collectors.toList());
    }

    @Override
    public Car searchById(int id) throws CarNotFoundException {
        return cars.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + id + " not found"));
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }

    public List<Car> getAllCars() {
        return new ArrayList<>(cars);
    }
}