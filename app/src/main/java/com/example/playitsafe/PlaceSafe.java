package com.example.playitsafe;

public class PlaceSafe {
    public String address;
    public String cleaning;
    public String cleaningcarts;
    public String distancing;
    public String limitingusers;
    public String masks;
    public String name;
    public String plexiglass;
    public String rating;
    public String type;


    public PlaceSafe(String address, String cleaning, String cleaningcarts, String distancing, String limitingusers, String masks, String name, String plexiglass, String rating, String type) {
        this.address = address;
        this.cleaning = cleaning;
        this.cleaningcarts = cleaningcarts;
        this.distancing = distancing;
        this.limitingusers = limitingusers;
        this.masks = masks;
        this.name = name;
        this.plexiglass = plexiglass;
        this.rating = rating;
        this.type = type;

    }

    public String getResult()
    {
        String result = "Name: "+this.name+" Address: "+this.address+" Type of Place: "+this.type+" Rating: "+this.rating+" Cleaning Store: "+this.cleaning+" Limiting Users: "+this.limitingusers+" Distancing Enforced: "+this.distancing+" Masks Required: "+this.masks+" Plexiglass for Employees: "+this.plexiglass+" Cleaning Carts: "+this.cleaningcarts;
        return result;
    }
}
