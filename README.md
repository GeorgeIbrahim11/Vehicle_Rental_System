# 🚗 Vehicle Rental System

A console-based Vehicle Rental Management System built with Java and Maven. The system allows managers to manage a fleet of vehicles (Cars, Bikes, and Vans) and handle customer rentals with full CRUD operations and custom exception handling.

---

## 📋 Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [How to Run](#how-to-run)
- [Classes Overview](#classes-overview)

---

## ✨ Features

- Add, view, update, and remove vehicles (Car, Bike, Van)
- Register and manage customers (including VIP customers)
- Rent and return vehicles with pricing calculation
- Custom exception handling (`CustomerNotFoundException`, `DuplicateVehicleIdException`, `VehicleNotFoundException`)
- Manager role with full system control
- Clean OOP design using inheritance and polymorphism

---

## 📁 Project Structure

```
Vehicle_Rental_System/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/mycompany/vehicle_rental_system/
                ├── AppLauncher.java
                ├── RentalSystemApp.java
                ├── VehicleRentalSystem.java
                ├── Vehicle.java
                ├── Car.java
                ├── Bike.java
                ├── Van.java
                ├── Customer.java
                ├── VipCustomer.java
                ├── Manager.java
                ├── Pricing.java
                ├── CustomerNotFoundException.java
                ├── DuplicateVehicleIdException.java
                └── VehicleNotFoundException.java
```

---

## 🛠 Technologies Used

- **Java** (JDK 11+)
- **Maven** (build & dependency management)
- Object-Oriented Programming (Inheritance, Polymorphism, Encapsulation)
- Custom Exception Handling

---

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- [Java JDK 11+](https://www.oracle.com/java/technologies/downloads/)
- [Apache Maven 3.6+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/)

### Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/Vehicle-Rental-System.git
cd Vehicle-Rental-System
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn exec:java -Dexec.mainClass="com.mycompany.vehicle_rental_system.AppLauncher"
```

Or run the generated JAR:

```bash
java -jar target/vehicle_rental_system-1.0-SNAPSHOT.jar
```

---

## 🧩 Classes Overview

| Class | Description |
|---|---|
| `Vehicle` | Abstract base class for all vehicle types |
| `Car`, `Bike`, `Van` | Concrete vehicle subclasses |
| `Customer` | Represents a standard customer |
| `VipCustomer` | Extends Customer with VIP privileges |
| `Manager` | Manages fleet and rental operations |
| `Pricing` | Handles rental pricing logic |
| `VehicleRentalSystem` | Core system logic and data management |
| `RentalSystemApp` | Main application menu/UI |
| `AppLauncher` | Entry point of the application |
| `*Exception` classes | Custom exceptions for error handling |

---

## 📌 Usage

On running the app, the console menu will allow you to:

1. Add a new vehicle to the fleet
2. View all available vehicles
3. Register a new customer
4. Rent a vehicle to a customer
5. Return a rented vehicle
6. View rental history and pricing

---

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you'd like to change.

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
