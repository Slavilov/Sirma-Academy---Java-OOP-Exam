package carrental.models;

public class Customer {
    private String name;
    private String id;

    public Customer(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public String getId() { return id; }

    @Override
    public String toString() {
        return String.format("Name: %s (ID: %s)", name, id);
    }
}