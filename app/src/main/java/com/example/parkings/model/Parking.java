package com.example.parkings.model;

public class Parking {
    private String Address, ID,Name;
    int Maxslot;
    public Parking(String Address, String  ID, String Name,int Maxslot){
        this.Address = Address;
        this.ID = ID;
        this.Name = Name;
        this.Maxslot = Maxslot;
    }
    public Parking(){

    }

    public int getMaxslot() {
        return Maxslot;
    }

    public void setMaxslot(int maxslot) {
        Maxslot = maxslot;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
