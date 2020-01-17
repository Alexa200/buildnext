package com.classroom.app1.Model;

import java.util.Map;

public class Builder {
    private String name;
    private String phone;
    private String address;
    private String description;
    private String id_builder;


    public Builder(String name, String phone, String address, String description) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.description = description;
    }

    public Builder(String id_builder,String name, String phone, String address, String description) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.description = description;
    }
    public Builder(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
