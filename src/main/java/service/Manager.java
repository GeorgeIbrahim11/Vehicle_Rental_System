/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vehicle_rental_system;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author USER
 */
public class Manager {
    private ArrayList<Vehicle> inventory; //Encapsulation
    private ArrayList<Customer> customers;

    // Constructor
    public Manager() {
        inventory = new ArrayList<>();
        customers = new ArrayList<>();
    }

    //Vehicle Management

    public void addVehicle(Vehicle newVehicle) throws DuplicateVehicleIdException {
        for (Vehicle v : inventory) {
            if (v.vehicleId == newVehicle.vehicleId) {
                throw new DuplicateVehicleIdException(
                        "Vehicle with ID " + newVehicle.vehicleId + " already exists."
                );
            }
        }
        inventory.add(newVehicle);
    }

    public void removeVehicle(int vehicleId) throws VehicleNotFoundException {
        Vehicle v = findVehicleById(vehicleId);
        inventory.remove(v);
    }

    public Vehicle findVehicleById(int vehicleId) throws VehicleNotFoundException {
        for (Vehicle v : inventory) {
            if (v.vehicleId == vehicleId) 
                return v;
        }
        throw new VehicleNotFoundException(
            "Vehicle with ID " + vehicleId + " not found."
        );
    }

    //Customer Management

    public void addCustomer(Customer customer) {   
        customers.add(customer);
    }
    
  
    public void removeCustomer(int customerId) throws CustomerNotFoundException {
        Customer c = findCustomerById(customerId);
        customers.remove(c);
    }
    
    public Customer findCustomerById(int customerId) throws CustomerNotFoundException {
        for (Customer c : customers) {
            if (c.getId() == customerId) {
                return c;
            }   
        }
        throw new CustomerNotFoundException(
            "Customer with ID " + customerId + " not found."
        );
    }


    public void upgradeCustomerToVIP(int customerId) {
    
        // Find the customer
        Customer customer = null;
        for (Customer c : customers) {
            if (c.getId() == customerId) {
                customer = c;
                break;
            }
        }
               
        // Create new VIP customer with same details
        VipCustomer vipCustomer = new VipCustomer(customer.getId(),customer.getName(),customer.getEmail(),customer.getPhone());
    
        // Transfer rented vehicles and total rent
        vipCustomer.totalRent = customer.totalRent;
        vipCustomer.rentedVehicles = customer.rentedVehicles;
        
        // Replace in list
        int index = customers.indexOf(customer);
        customers.set(index, vipCustomer);
    }
   
    //Rental Operations
    
    public void processRental(Customer customer, int vehicleId, int days) throws VehicleNotFoundException {

        Vehicle vehicle = findVehicleById(vehicleId);
        double price = vehicle.calculateRent(customer, days); //Polymorphism
        customer.rentVehicle(vehicle);
        vehicle.setAvailable(false);    
        String type = vehicle.getTypeName();
        if (customer instanceof VipCustomer)
            System.out.println("VipCustomer "+customer.getName()+" rented a "+type+" of id: "+vehicleId+
                " with price "+price+" and discount of "+vehicle.discount);
        else
            System.out.println("Customer "+customer.getName()+" rented a "+type+" of id: "+vehicleId+
                " with price "+price);
                
    }

    public void processReturn(Customer customer, int vehicleId) throws VehicleNotFoundException{

        Vehicle vehicle = findVehicleById(vehicleId);

        customer.returnVehicle(vehicle);
        vehicle.setAvailable(true);
    }

    //Maintenance management
    public void processMaintenance(Vehicle vehicle){
        vehicle.setAvailable(false);
        vehicle.performMaintenance();
        vehicle.setAvailable(true);
    }
    
    //Display
    public void displayAllVehicles() {
        for (Vehicle v : inventory) {
            v.displayInfo(); // polymorphism
            System.out.println("--------------------");
        }
    }
    
    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println(c.toString());  // polymorphism
            System.out.println("--------------------");
        }
    }
    // Generic sorting using compareTo method
    public void sortInventory() {
        Collections.sort(inventory);
    }
}
