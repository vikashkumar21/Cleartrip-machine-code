package com.example.demo.dao;

import com.example.demo.model.Booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDao {

    private final Map<String, List<Booking>> bookedDetails;

    public BookingDao() {
        this.bookedDetails = new HashMap<>();
    }

    public List<Booking> getBookedDetails(final String customerName) {
        return bookedDetails.get(customerName);
    }

    public void addBooking(final String customerName, final List<Booking> bookings) {
        bookedDetails.put(customerName, bookings);
    }

    public void removeBooking(final String customerName, final Booking booking) {
        bookedDetails.get(customerName).remove(booking);
    }
}