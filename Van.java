/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vehicle_rental_system;

/**
 *
 * @author USER
 */
public class Van extends Vehicle {
    private final static int numTires=4;
    private double cargoCapacity;
    private double maxLoad;
    Van(){
        
    }
    Van(int id,String name,String model,double price,double capacity,double load,double discount){
        super(id,name,model,price);
        cargoCapacity=capacity;
        maxLoad=load;
        this.discount=discount;
    }
    @Override
    public void displayInfo(){
        System.out.printf("Van_ID=%d,manufactured by=%s,model=%s,rent_price/day=%f,capacity=%f,max load =%f",super.vehicleId,
        super.companyName,super.model,super.rentPricePerDay,cargoCapacity,maxLoad);
        System.out.println();
    }
    @Override
    public String getTypeName() {
        return "Van";
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
        //putting the van out of service
        this.setAvailable(false);
        System.out.println("Van is under maintenance");
        System.out.println("Maintenance is completed");
        //the van is in service again
        this.setAvailable(true);
    }
}
