package com.example.robin.demoapp.test;

import java.io.Serializable;

public class Shop implements Serializable {

    private String name;                // Nom du magasin
    private String address;             // Adresse du magasin
    private String shopSpeciality;      // Spécialité du magasin (nourriture, vêtements, etc.)

    public Shop() {
    }

    public Shop(String name, String address, String shopSpeciality) {
        this.name = name;
        this.address = address;
        this.shopSpeciality = shopSpeciality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopSpeciality() {
        return shopSpeciality;
    }

    public void setShopSpeciality(String shopSpeciality) {
        this.shopSpeciality = shopSpeciality;
    }
}
