package com.example.hms.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import org.springframework.data.annotation.Id;


@Entity
@Table(name = "Hospital")
public  class Hospital implements IModelHospital {

    @Id
    private int id;
    private String name;
    private int totalBeds;
    private int availableBeds;
    private int port;
    private String host;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    @Override
    public int getTotalBeds() {
        return totalBeds;
    }

    @Override
    public void setTotalBeds(int totalBeds) {
        this.totalBeds = totalBeds;
    }

    @Override
    public int getAvailableBeds() {
        return availableBeds;
    }

    @Override
    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String ipAddress) {
        this.host = ipAddress;
    }

    public void setName(String name) {
        this.name = name;
    }
}




