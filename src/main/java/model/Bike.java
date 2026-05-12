/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vehicle_rental_system;

/**
 *
 * @author USER
 */
public class Bike extends Vehicle {
    private int numTires;
    private int engineCC;
    private boolean hasSideCar;
    Bike (){
        
    }
    Bike(int id,String name,String model,double price,int num,int engine,boolean sideCar,double discount){
        super(id,name,model,price);
        numTires=num;
        engineCC=engine;
        hasSideCar=sideCar;
        this.discount=discount;
    }
    @Override
    public void displayInfo(){
        System.out.printf("Bike_ID=%d,manufactured by=%s,model=%s,no of tires=%d,engineCC=%d,rent_price/day=%f",
        super.vehicleId,super.companyName,super.model,numTires,engineCC,super.rentPricePerDay);
        System.out.println();
        if(hasSideCar)
           System.out.println("It has a side car");
        else
            System.out.println("It doesn't have a side car");
    }
    @Override
    public String getTypeName() {
        return "Bike";
    }
    @Override
    public double calculateRent(Customer c,int days){
         
        if (c instanceof VipCustomer && !hasSideCar)
            return (rentPricePerDay * days) * (1.0-discount);
        else if (c instanceof VipCustomer && hasSideCar)
            return ((rentPricePerDay * days)+150) * (1.0-discount);
        else if (hasSideCar)
            return (rentPricePerDay * days) + 150;
        else
            return rentPricePerDay * days;
    }
    
    @Override
    public void performMaintenance(){
        //putting the Bike out of service
        this.setAvailable(false);
        System.out.println("Bike is under maintenance");
        System.out.println("Maintenance is completed");
        //the Bike is in service again
        this.setAvailable(true);
    }
}
