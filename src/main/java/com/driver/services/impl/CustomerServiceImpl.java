package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer=customerRepository2.findById(customerId).get();

		customerRepository2.delete(customer);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		Customer customer=customerRepository2.findById(customerId).get();
		List<Driver>driverList=driverRepository2.findAll();


		Driver driver=null;

		for(Driver d:driverList){

			if(d.getCab().isAvailable()){
				driver=d;
				break;
			}

		}
		if(driver==null){
			throw new Exception("Driver No available");
		}
		int bill=driver.getCab().getPerKmRate()*distanceInKm;

		TripBooking booktrip=new TripBooking(fromLocation,toLocation,distanceInKm,TripStatus.CONFIRMED,bill,customer);
		driver.getCab().setAvailable(false);

		driver.getTripBookingList().add(booktrip);

		driverRepository2.save(driver);

		TripBooking t= tripBookingRepository2.save(booktrip);
		return t;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
		tripBooking.setTripStatus(TripStatus.CANCELED);

		tripBooking.setBill(0);
		Driver driver=tripBooking.getDriver();
		driver.getCab().setAvailable(true);

		driverRepository2.save(driver);
		tripBookingRepository2.save(tripBooking);


	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking trip=tripBookingRepository2.findById(tripId).get();
		trip.setTripStatus(TripStatus.COMPLETED);

		Driver driver=trip.getDriver();

		driver.getCab().setAvailable(true);

		driverRepository2.save(driver);
		tripBookingRepository2.save(trip);

	}
}
