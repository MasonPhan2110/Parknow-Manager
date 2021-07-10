package com.example.parkings.model;

public class Parks {
    String email,id,id_park,phone,username;
    public Parks(String email,String id,String id_park,String phone,String username){
        this.email = email;
        this.id = id;
        this.id_park = id_park;
        this.phone = phone;
        this.username = username;
    }
    public Parks(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_park() {
        return id_park;
    }

    public void setId_park(String id_park) {
        this.id_park = id_park;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
