package com.driver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="driver")

public class Driver{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int driverId;

    private  String mobileNo;

    private String password;


    @OneToOne
    @JoinColumn
    private Cab cab;

    @OneToMany(mappedBy ="driver",cascade = CascadeType.ALL)
    private List<TripBooking>tripBookingList=new ArrayList<>();

    public Driver(int driverId, String mobileNo, String password, Cab cab, List<TripBooking> tripBookingList) {
        this.driverId = driverId;
        this.mobileNo = mobileNo;
        this.password = password;
        this.cab = cab;
        this.tripBookingList = tripBookingList;
    }

    public Driver() {
    }


    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }
}