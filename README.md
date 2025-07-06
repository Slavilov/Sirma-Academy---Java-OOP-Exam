Car Rental System - README
Overview

This Java application is a console-based Car Rental System that demonstrates Object-Oriented Programming (OOP) principles. The system allows users to manage a fleet of rental cars, handle rental transactions, and persist data to a CSV file.
Architecture Overview

The application follows a layered architecture with clear separation of concerns:

    Models: Contain the core data structures (Car, Customer, Rental, VehicleType)

    Interfaces: Define contracts for key functionalities (Rentable, Searchable)

    Services: Implement business logic and file operations

    Exceptions: Custom exception classes for error handling

    Main App: Coordinates all components and handles user interaction

Component Breakdown
1. Model Classes
Car.java

    Represents a vehicle in the rental fleet

    Contains properties: id, make, model, year, type, availability status

    Manages rental state through rentCar() and returnCar() methods

    Enforces business rules (e.g., can't rent an already rented car)

Customer.java

    Represents a person renting a car

    Simple class with name and ID properties

Rental.java

    Represents a rental transaction

    Tracks car, customer, and rental dates

    Handles rental completion with completeRental()

VehicleType.java

    Enumeration of vehicle categories (Sedan, Hatchback, etc.)

    Includes parsing logic for string-to-enum conversion

2. Interface Classes
Rentable.java

    Defines contract for rental operations:

        rentCar() - initiates a rental

        returnCar() - completes a rental

Searchable.java

    Defines contract for search operations:

        Various search methods (by model, make, type, etc.)

3. Service Classes
CarFileService.java

    Handles all CSV file operations

    Implements custom CSV parsing without external libraries

    Converts between Car objects and CSV format

    Includes robust error handling for file operations

CarRentalService.java

    Core business logic implementation

    Implements both Rentable and Searchable interfaces

    Manages the collection of cars

    Provides methods for all system operations (add, remove, search, etc.)

RentalManager.java

    Handles user interaction and command processing

    Parses user input and delegates to CarRentalService

    Provides formatted output to console

    Includes help system and input validation

4. Exception Classes

Custom exceptions for specific error cases:

    CarNotFoundException: When a requested car doesn't exist

    InvalidDataException: For data format/validation errors

    RentalException: For rental-specific business rule violations

5. Main Application Class
CarRentalApp.java

    Entry point for the application

    Initializes all components

    Manages application lifecycle

    Handles top-level exceptions

Data Flow

    Startup:

        CarRentalApp creates CarFileService and loads data from CSV

        Initializes CarRentalService with loaded data

        Creates RentalManager with the service

    User Interaction:

        User enters commands in console

        RentalManager parses and executes commands

        Commands delegate to CarRentalService methods

        Results are formatted and displayed

    Persistence:

        Changes are kept in memory during operation

        On exit, CarFileService writes all data back to CSV

Key Design Patterns

    Interface Segregation Principle:

        Separate interfaces for rental and search operations

        Allows for flexible implementation changes

    Single Responsibility Principle:

        Each class has a clear, focused purpose

        Separation of file I/O, business logic, and UI

    Dependency Injection:

        Services are injected into dependent classes

        Enables easier testing and modularity

    Command Pattern:

        RentalManager handles different commands uniformly

        Easy to add new commands without changing existing code

Data Persistence

The system uses a CSV file (cars.csv)

Usage Instructions
Available Commands:

    add - Add a new car to the fleet

    edit <id> - Modify an existing car's details

    remove <id> - Remove a car from the fleet

    list - Display all cars

    rent <carId>,<customerName>,<customerId> - Rent a car

    return <carId> - Return a rented car

    search <criteria> <value> - Search cars (criteria: model, make, type, id, status)

    help - Show available commands

    exit - Save and quit

Example Usage:
text

> add
Enter ID: 4
Enter Make: Honda
Enter Model: Civic
Enter Year: 2021
Enter Type: Sedan
Car added successfully!

> rent 4,John Doe,CUST002
Enter rental start date (yyyy-MM-dd): 2025-07-01
Enter expected return date (yyyy-MM-dd): 2025-07-08
Car rented successfully!

> list
ID: 1 | Make: Toyota | Model: Corolla | Year: 2019 | Type: Sedan | Status: Available
ID: 2 | Make: Ford | Model: Focus | Year: 2020 | Type: Hatchback | Status: Rented by Jane Smith
ID: 3 | Make: Audi | Model: A4 | Year: 2022 | Type: Sedan | Status: Available
ID: 4 | Make: Honda | Model: Civic | Year: 2021 | Type: Sedan | Status: Rented by John Doe

> exit
Data saved successfully. Goodbye!

Error Handling

The system includes comprehensive error handling:

    Invalid user input

    File I/O errors

    Business rule violations (e.g., renting an already rented car)

    Data consistency checks

Errors are caught and presented to the user with helpful messages while preventing application crashes.
