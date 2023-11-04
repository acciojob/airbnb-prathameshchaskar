package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class HotelRepository {

    HashMap<String, Hotel> hotelDatabase;
    HashMap<Integer, User> userDatabase;
    HashMap<String, Booking> bookingDatabase;
    HashMap<Integer, List<String>> adharVsBookings;

    public HotelRepository(){
        this.hotelDatabase = new HashMap<>();
        this.userDatabase = new HashMap<>();
        this.bookingDatabase = new HashMap<>();
        this.adharVsBookings = new HashMap<>();
    }

    public String addHotel(Hotel obj){
        String hotelName = obj.getHotelName();
        if(hotelName==null || obj ==null ) {
            return "FAILURE";
        }
        else{
            hotelDatabase.put(hotelName, obj);
            return "SUCCESS";
        }
    }

    public Integer addUser(User obj){
        int aadharNo = obj.getaadharCardNo();
        if(!userDatabase.containsKey(aadharNo)){
            userDatabase.put(aadharNo,obj);
        }
        return aadharNo;
    }

    public String getHotelWithMostFacilities(){

        if(hotelDatabase.size()==0) {
            return "";
        }
        String hotelWithMostFacilities = "";
        int maxFacilities = 0;

        for(String hotel : hotelDatabase.keySet()) {
            int facilities = hotelDatabase.get(hotel).getFacilities().size();
            if(facilities > maxFacilities) {
                hotelWithMostFacilities = hotel;
                maxFacilities = facilities;
            }
            else if(maxFacilities > 0 && facilities==maxFacilities) {
                if (hotelWithMostFacilities.compareTo(hotel) > 0) {
                    hotelWithMostFacilities = hotel;
                }
            }
        }
        return hotelWithMostFacilities;
    }

    public int bookARoom(Booking booking){
        int roomsRequired = booking.getNoOfRooms();
        String hotelName = booking.getHotelName();

        if(hotelDatabase.containsKey(hotelName) && roomsRequired <= hotelDatabase.get(hotelName).getAvailableRooms()){
            Hotel hotel = hotelDatabase.get(hotelName);
            String bId = UUID.randomUUID().toString();
            int amountToBePaid = roomsRequired * hotel.getPricePerNight();

            booking.setBookingId(bId);
            booking.setAmountToBePaid(amountToBePaid);

            bookingDatabase.put(bId, booking);

            hotel.setAvailableRooms(hotel.getAvailableRooms()-roomsRequired);

            int aadharNo = booking.getBookingAadharCard();
            if(!adharVsBookings.containsKey(aadharNo)){
                adharVsBookings.put(aadharNo, new ArrayList<>());
            }
            adharVsBookings.get(aadharNo).add(bId);

            return amountToBePaid;
        }

        return -1;
    }
    public int getBookings(Integer adharcard){
        if(adharVsBookings.containsKey(adharcard)){
            return adharVsBookings.get(adharcard).size();
        }
        return 0;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        if (hotelDatabase.containsKey(hotelName)) {
            Hotel hotel = hotelDatabase.get(hotelName);
            List<Facility> oldFacilities = hotel.getFacilities();

            for (Facility facility : newFacilities) {
                if (!oldFacilities.contains(facility)) {
                    oldFacilities.add(facility);
                }
            }

            hotel.setFacilities(oldFacilities);

            return hotel;
        }

        return null;


    }

}
