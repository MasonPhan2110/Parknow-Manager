package com.example.parkings.model;

public class Users {
    private String username, email, phone, vehicle, plate, id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users(String username, String email, String phone, String vehicle, String plate, String id) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.vehicle = vehicle;
        this.plate = plate;
        this.id = id;
    }
    public Users(){

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
