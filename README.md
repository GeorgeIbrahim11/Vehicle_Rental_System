# 🚗 Vehicle Rental System

A console-based Vehicle Rental Management System built with Java. The system allows managers to manage a fleet of vehicles (Cars, Bikes, and Vans) and handle customer rentals with full CRUD operations and custom exception handling.

---

## 📋 Table of Contents

- [Features](#-features)
- [Project Structure](#-project-structure)
- [Technologies Used](#-technologies-used)
- [Getting Started](#-getting-started)
- [How to Run](#-how-to-run)
- [Classes Overview](#-classes-overview)

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
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       ├── app/
    │       │   ├── AppLauncher.java
    │       │   └── RentalSystemApp.java
    │       ├── model/
    │       │   ├── Vehicle.java
    │       │   ├── Car.java
    │       │   ├── Bike.java
    │       │   ├── Van.java
    │       │   ├── Customer.java
    │       │   └── VipCustomer.java
    │       ├── service/
    │       │   ├── VehicleRentalSystem.java
    │       │   ├── Manager.java
    │       │   └── Pricing.java
    │       └── exception/
    │           ├── CustomerNotFoundException.java
    │           ├── DuplicateVehicleIdException.java
    │           └── VehicleNotFoundException.java
    └── test/
        └── java/
            └── test/
                └── VehicleRentalTest.java
```

---

## 🛠 Technologies Used

- Java (JDK 11+)
- Object-Oriented Programming (Inheritance, Polymorphism, Encapsulation)
- Custom Exception Handling
- JUnit (Testing)

---

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- Java JDK 11+
- Git

### Clone the Repository

```bash
git clone https://github.com/GeorgeIbrahim11/Vehicle_Rental_System.git
cd Vehicle_Rental_System
```

### Compile the Project

```bash
javac -d out src/main/java/model/*.java src/main/java/exception/*.java src/main/java/service/*.java src/main/java/app/*.java
```

### Run the Application

```bash
java -cp out app.AppLauncher
```

---

## 🧩 Classes Overview

| Package | Class | Description |
|---|---|---|
| `model` | `Vehicle` | Abstract base class for all vehicle types |
| `model` | `Car`, `Bike`, `Van` | Concrete vehicle subclasses |
| `model` | `Customer` | Represents a standard customer |
| `model` | `VipCustomer` | Extends Customer with VIP privileges |
| `service` | `VehicleRentalSystem` | Core system logic and data management |
| `service` | `Manager` | Manages fleet and rental operations |
| `service` | `Pricing` | Handles rental pricing logic |
| `app` | `RentalSystemApp` | Main application menu/UI |
| `app` | `AppLauncher` | Entry point of the application |
| `exception` | `*Exception` | Custom exceptions for error handling |
| `test` | `VehicleRentalTest` | Unit tests for core functionality |

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

This project is open-source and available under the MIT License.
