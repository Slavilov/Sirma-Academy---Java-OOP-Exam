package carrental.models;

public enum VehicleType {
    SEDAN("Sedan"),
    HATCHBACK("Hatchback"),
    SUV("SUV"),
    TRUCK("Truck"),
    VAN("Van");

    private final String displayName;

    VehicleType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static VehicleType fromString(String text) {
        for (VehicleType type : VehicleType.values()) {
            if (type.displayName.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}