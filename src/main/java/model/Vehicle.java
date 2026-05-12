/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.vehicle_rental_system;

/**
 *
 * @author USER
 */
public abstract class Vehicle implements Comparable<Vehicle>, Pricing {
    //Attributes
    protected int vehicleId;
    protected String companyName;
    protected String model;
    protected double rentPricePerDay;
    protected boolean isAvailable;
    protected double discount;

    //No-Arg Constructor according to design guidlines
    Vehicle () {
        
    }
    
//Constructor
    Vehicle (int id,String name,String model,double price){
        vehicleId=id;
        companyName=name;
        this.model=model;
        rentPricePerDay=price;
        this.isAvailable = true;
    }
    
    //Methods
    public abstract void displayInfo();//Polymorphism
    public abstract void performMaintenance();//Polymorphism
    public abstract String getTypeName();//Polymorphism
    public abstract double calculateRent(Customer c, int days);//Polymorphism
    
    
    public boolean checkAvailability() {
        return isAvailable;
    }
    
    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }
    
    @Override
    public int compareTo(Vehicle other) {
    // Overriding the method from interface to sort Vehicles by ID
    // return negative if the arg ID is bigger
    // return positive if the arg ID is smaller
    return this.vehicleId - other.vehicleId;
    }
}    
    