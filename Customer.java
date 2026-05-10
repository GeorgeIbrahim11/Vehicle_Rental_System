/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vehicle_rental_system;
import java.util.ArrayList;
/**
 *
 * @author USER
 */
public class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;
    protected int totalRent;
    protected ArrayList<Vehicle> rentedVehicles;

    //No-Arg Constructor according to design guidlines
    public Customer(){
        
    }
    
    //constructor
    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.totalRent = 0;
        rentedVehicles = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
    
    
    // Rent a vehicle
    public void rentVehicle(Vehicle vehicle) {
        this.totalRent += 1;
        rentedVehicles.add(vehicle);
    }
    
    //Return a vehicle
    public void returnVehicle(Vehicle vehicle) {
        rentedVehicles.remove(vehicle);
    }

  

    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", phone='" + phone + 
                '\'' + ", totalRent=" + totalRent + '}';
    }
}
