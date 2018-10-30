package com.example.robin.demoapp.test;

import java.io.Serializable;

public class Vehicle implements Serializable {

    private String id;      // Id du véhicule
    private String brand;   // Marque du véhicule
    private String model;   // Modèle du véhicule

    public Vehicle() {
    }

    public Vehicle(String id, String brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
