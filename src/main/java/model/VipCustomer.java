/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vehicle_rental_system;

/**
 *
 * @author USER
 */
public class VipCustomer extends Customer {
    

    //No-Arg Constructor according to design guidlines
    public VipCustomer(){
        
    }
    
    //constructor to copy customer info
    public VipCustomer(int id, String name, String email, String phone) {
        super(id,name,email,phone);
    }

    @Override
    public String toString() {
        return "VIPCustomer{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +", email='" + this.getEmail() + '\'' + ", phone='" + this.getPhone() +'\'' +
                ", totalRent=" + totalRent + '}';
    }
}
