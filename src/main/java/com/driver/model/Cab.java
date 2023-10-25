package com.driver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="cab")

public class Cab{
    public Cab(int id, int perKmRate, boolean isAvailable, Driver driver) {
        this.id = id;
        this.perKmRate = perKmRate;
        this.isAvailable = isAvailable;
        this.driver = driver;
    }

    public Cab() {
    }


    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    private int perKmRate;

    private  boolean isAvailable;


    @OneToOne(mappedBy = "cab",cascade = CascadeType.ALL)
    private Driver driver;

    public void setId(int id) {
        this.id = id;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }



}