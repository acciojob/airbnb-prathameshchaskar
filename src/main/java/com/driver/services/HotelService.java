package com.driver.services;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepo;


    public String addHotel(Hotel obj) {
        return hotelRepo.addHotel(obj);
    }
    public Integer addUser(User obj) {
        return hotelRepo.addUser(obj);
    }
    public String getHotelWithMostFacilities(){
        return hotelRepo.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking){
        return hotelRepo.bookARoom(booking);
    }

    public int getBookings(Integer adharCard){
        return hotelRepo.getBookings(adharCard);
    }


    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName){
        return hotelRepo.updateFacilities(newFacilities, hotelName);
    }
}
