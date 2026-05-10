/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vehicle_rental_system;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Test for Manager Class.
 * Coverage and Testing of all methods and Exceptions .
 */
public class VehicleRentalTest {

    private Manager manager;
    private Car car;
    private Bike bike;
    private Van van;
    private Customer regularC;
    private VipCustomer vipC;

    @BeforeEach
    public void setUp() {
        manager = new Manager();

        
        car = new Car(1, "Toyota", "Camry", 100.0, 4, "Sedan", 0.10);
        bike = new Bike(2, "Harley", "Cruiser", 50.0, 2, 600, true, 0.20);
        van = new Van(3, "Ford", "Transit", 200.0, 1000, 500, 0.15);

        regularC = new Customer(101, "John", "john@regular.com", "12345");
        vipC = new VipCustomer(202, "Sarah", "sarah@vip.com", "67890");
        
    }

    // --- Vehicle Management Tests ---
    
    @Test
    @DisplayName("Test Adding and Finding different Vehicle types")
    public void testAddAndFindVehicle() throws DuplicateVehicleIdException, VehicleNotFoundException {
        manager.addVehicle(car);
        manager.addVehicle(bike);

        assertEquals(car, manager.findVehicleById(1), "Should find Car with ID 1");
        assertEquals(bike, manager.findVehicleById(2), "Should find Bike with ID 2");
    }

    @Test
    @DisplayName("Test Removing a Vehicle")
    public void testRemoveVehicle() throws DuplicateVehicleIdException, VehicleNotFoundException {
        manager.addVehicle(van);
        manager.removeVehicle(3);

        
        assertThrows(VehicleNotFoundException.class, () -> {
            manager.findVehicleById(3);
        }, "Van should be removed from inventory");
    }

    @Test
    @DisplayName("Exception: Removing a Missing Vehicle should fail")
    public void testRemoveMissingVehicle() {
        assertThrows(VehicleNotFoundException.class, () -> {
            manager.removeVehicle(999);
        }, "Should throw VehicleNotFoundException when removing non-existent ID");
    }

    @Test
    @DisplayName("Exception: Adding Duplicate ID should fail")
    public void testAddDuplicateException() throws DuplicateVehicleIdException {
        manager.addVehicle(car); 
        assertThrows(DuplicateVehicleIdException.class, () -> {
            manager.addVehicle(new Car(1, "Honda", "Civic", 100.0, 4, "Fuel Car", 0.1));
        }, "Should throw DuplicateVehicleIdException");
    }

    // ---  Customer Management Tests ---

    @Test
    @DisplayName("Test Adding and Finding Customers (Regular & VIP)")
    public void testAddAndFindCustomer() throws CustomerNotFoundException {
        manager.addCustomer(regularC);
        manager.addCustomer(vipC);

        assertEquals(regularC, manager.findCustomerById(101),"Should find Customer with ID 101");
        assertEquals(vipC, manager.findCustomerById(202),"Should find Vip Customer with ID 202");
    }

    @Test
    @DisplayName("Test Removing Regular and VIP Customers")
    public void testRemoveRegularAndVipCustomer() throws CustomerNotFoundException {
        
        manager.addCustomer(regularC);
        manager.addCustomer(vipC);

        
        manager.removeCustomer(101);
        
        
        assertThrows(CustomerNotFoundException.class, () -> {
            manager.findCustomerById(101);
        }, "Regular Customer should be removed");

        
        manager.removeCustomer(202);
        
        assertThrows(CustomerNotFoundException.class, () -> {
            manager.findCustomerById(202);
        }, "VIP Customer should be removed");
    }

    @Test
    @DisplayName("Exception: Removing a Missing Customer should fail")
    public void testRemoveMissingCustomer() {
        assertThrows(CustomerNotFoundException.class, () -> {
            manager.removeCustomer(999);
        }, "Should throw CustomerNotFoundException when removing non-existent ID");
    }

    @Test
    @DisplayName("Test Upgrading Regular Customer to VIP")
    public void testUpgradeCustomerToVIP() throws CustomerNotFoundException {
        manager.addCustomer(regularC);
        manager.upgradeCustomerToVIP(101);

        regularC = manager.findCustomerById(101);
        assertTrue(regularC instanceof VipCustomer, "Customer should be instance of VipCustomer after upgrade");
    }

    // --- 3. Rental Process Tests ---

    @Test
    @DisplayName("Test Process Rental (Regular Customer + Car),(Vip Customer + Van)")
    public void testProcessRental() throws DuplicateVehicleIdException, VehicleNotFoundException {
        // Regular Customer renting
        manager.addVehicle(car);
        assertTrue(car.checkAvailability(), "Car should be available initially");
        manager.processRental(regularC, 1, 5);
        
       //Price 100.0 * 5 = 500.0
        assertEquals(500.0, car.calculateRent(regularC, 5), "Rent Price should be 500.0 EGP");
        assertFalse(car.checkAvailability(), "Car should be UNAVAILABLE after rent");
        assertEquals(1, regularC.rentedVehicles.size(),"Rented vehicle array size should be 1");
        
        //Vip Customer renting
        manager.addCustomer(vipC);
        manager.addVehicle(van);
        assertTrue(van.checkAvailability(), "Van should be available initially");
        manager.processRental(vipC, 3, 2);
        
        //Price (200 * 2 ) * 0.85= 340.0
        assertEquals(340.0, van.calculateRent(vipC, 2), "Rent Price should be 340.0 EGP");
        assertFalse(van.checkAvailability(), "Van should be UNAVAILABLE after rent");
        assertEquals(1, vipC.rentedVehicles.size());
    }

    
    @Test
    @DisplayName("Test Process Return")
    public void testProcessReturn() throws DuplicateVehicleIdException, VehicleNotFoundException {
        manager.addVehicle(bike);
        manager.processRental(regularC, 2, 2); 
        
        manager.processReturn(regularC, 2);

        assertTrue(bike.checkAvailability(), "Bike should be AVAILABLE after return");
    }

    // --- Maintenance and Sorting ---

    @Test
    @DisplayName("Test Maintenance Process")
    public void testProcessMaintenance() throws DuplicateVehicleIdException {
        manager.addVehicle(car);
        manager.processMaintenance(car);
        assertTrue(car.checkAvailability()," Car should be AVAILABLE after maintenance");
    }

    @Test
    @DisplayName("Test Sorting Inventory (Car, Bike, Van)")
    public void testSortInventory() throws DuplicateVehicleIdException {
        manager.addVehicle(van);  // ID 3
        manager.addVehicle(car);  // ID 1
        manager.addVehicle(bike); // ID 2

        manager.sortInventory();

        assertTrue(car.compareTo(bike) < 0, "ID 1 < ID 2");
        assertTrue(bike.compareTo(van) < 0, "ID 2 < ID 3");
    }

}