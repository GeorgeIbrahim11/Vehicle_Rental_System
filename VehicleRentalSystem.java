/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
 package com.mycompany.vehicle_rental_system;
 
public class VehicleRentalSystem {

    public static void main(String[] args) throws CustomerNotFoundException, VehicleNotFoundException {
   
        Manager system = new Manager();
        Vehicle v1 = new Car(305, "Toyota", "Camry", 100.0, 4, "fuel car", 0.30); 
        Vehicle v2 = new Van(101, "Ford", "Transit", 400.0, 150.0, 1000.0, 0.50); 
        Vehicle v3 = new Bike(202, "Harley", "Street", 200.0, 2, 1000, false, 0.20);
        Vehicle v4 = new Car(101, "Mercedes", "x6", 700.0, 4, "fuel car", 0.20);
        
        //Exception handling for adding to vehicles with same ID
        try{
            system.addVehicle(v1);
            system.addVehicle(v2);
            system.addVehicle(v3);
            system.addVehicle(v4);
        }    
        catch (DuplicateVehicleIdException e) {
            System.out.println("EXCEPTION CAUGHT: " + e.getMessage());
        }

        
        system.displayAllVehicles();//Polymorphism
        
        //Generic sorting by ID
        System.out.println("----------------------Sorting---------------------");
        system.sortInventory(); // Calls Collections.sort() using compareTo
        system.displayAllVehicles();

        //Creating customers
        Customer c1 = new Customer(150,"Ahmed","Ahemd@gmail.com","013435654");
        Customer c2 = new Customer(151,"Mina","Mina@gmail.com","0134000654");
        Customer c3 = new VipCustomer(152,"Sarah","Sarah@gmail.com","01342222");
        system.addCustomer(c1);
        system.addCustomer(c2);
        system.addCustomer(c3);
        
        //Displaying info of Customers
         system.displayAllCustomers();
        
        //Upgrading Mina to Vip
        system.upgradeCustomerToVIP(151);
        c2=system.findCustomerById(151);
        
        try { 
            System.out.println("---------------------Renting-----------------");
            // Renting the Vehicles
            //Applying Polymorphism on calculate rent price method whic is called in process rental
            
            //Regular Customer rents car (ID 305)
            // Expected: Full Price (100 * 2 days = 200EGP)
            system.processRental(c1, 305, 2);

            //updated to VIP Customer rents bike without sidecar (ID 202)
            // Expected: Price is Discounted by 20% (200 * 5 * 0.80 = 800EGP)
            system.processRental(c2, 202, 5);

            //VIP Customer rents Van (ID 101)
            // Expected: Price is discounted by 50% ( 400 * 15 ) * 0.5 = 3000EGP)
            system.processRental(c3, 101, 15);
        }    
        catch (VehicleNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("-----------------------------------------------");
        //Displaying info of Customers after renting
        system.displayAllCustomers();
        
        System.out.println("-----------------------------------------------");

        //checking availability of Vehicles after being rent
        //Expected : false for all (not available)
        boolean b1 = v1.checkAvailability();
        boolean b2 = v2.checkAvailability();
        boolean b3 = v3.checkAvailability();
        if (!b1)
            System.out.println("car isn't available");
        else
             System.out.println("car is available");
        if (!b2)
            System.out.println("Van isn't available");
        else
             System.out.println("Van is available");
        if (!b3)
            System.out.println("Bike isn't available");
        else
             System.out.println("Vike is available");
        
        //Returning car,van
        System.out.println("---------------------Returning------------------");
        system.processReturn(c1, 305);
        system.processReturn(c3, 101);
        
        //checking availability of Vehicles after some of them returned
        //Expected : car and van are available but bike isn't available
        b1 = v1.checkAvailability();
        b2 = v2.checkAvailability();
        b3 = v3.checkAvailability();
        if (!b1)
            System.out.println("car isn't available");
        else
             System.out.println("car is available");
        if (!b2)
            System.out.println("Van isn't available");
        else
             System.out.println("Van is available");
        if (!b3)
            System.out.println("Bike isn't available");
        else
             System.out.println("Vike is available");
        
        // Performing Maintenance for car and van
        System.out.println("---------------------Maintenance----------------");
        system.processMaintenance(v1);
        system.processMaintenance(v2);
        
        try{
            
        //Removing the car from the inventory
            system.removeVehicle(305);
            system.findVehicleById(305);

        }
        catch (VehicleNotFoundException e){
            System.out.println("EXCEPTION CAUGHT: " + e.getMessage());
        }
    }    
}