/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vehicle_rental_system;



/**
 *
 * @author USER
 */
public class Car extends Vehicle {
    private int numDoors;
    private final static int numTires=4;
    private String type;
    
    Car(){
        
    }
    
    Car(int id,String name,String model,double price,int doors,String type,double discount){
        super(id,name,model,price);
        numDoors=doors;
        this.type=type;
        this.discount=discount;
    }
    
    @Override
    public void displayInfo(){
        System.out.printf("Car_ID=%d,manufactured by=%s,model=%s,no of doors=%d,type=%s,rent_price/day=%f",super.vehicleId,
        super.companyName,super.model,numDoors,type,super.rentPricePerDay);
        System.out.println();
    }
    
    @Override
    public String getTypeName() {
        return "Car";
    }
    
    @Override
    public double calculateRent(Customer c,int days){
        // discount for VIP Customer 
        if (c instanceof VipCustomer)
            return (rentPricePerDay * days) * (1.0-discount);
        else
            return rentPricePerDay * days;
    }
  
    @Override
    public void performMaintenance(){
        //putting the car out of service
        this.setAvailable(false);
        System.out.println("Car is under maintenance");
        System.out.println("Maintenance is completed");
        //the car is in service again
        this.setAvailable(true);
    }
}
