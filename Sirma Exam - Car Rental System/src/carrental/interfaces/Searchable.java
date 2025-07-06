package carrental.interfaces;

import carrental.models.Car;
import java.util.List;

public interface Searchable {
    List<Car> searchByModel(String model);
    List<Car> searchByMake(String make);
    List<Car> searchByType(String type);
    List<Car> searchAvailable(boolean available);
    Car searchById(int id);
}