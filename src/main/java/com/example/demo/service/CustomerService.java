package com.example.demo.service;

import com.example.demo.dao.BookingDao;
import com.example.demo.dao.CentreDao;
import com.example.demo.dao.WorkoutDao;
import com.example.demo.exception.BookingDoesNotExistException;
import com.example.demo.exception.BookingNotAllowedException;
import com.example.demo.model.Booking;
import com.example.demo.model.Centre;
import com.example.demo.model.Timing;
import com.example.demo.model.Workout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CustomerService {

    private final CentreDao centreDao;
    private final WorkoutDao workoutDao;
    private final BookingDao bookingDao;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public CustomerService(final CentreDao centreDao,
                           final WorkoutDao workoutDao,
                           final BookingDao bookingDao) {
        this.centreDao = centreDao;
        this.workoutDao = workoutDao;
        this.bookingDao = bookingDao;
    }

    public List<Workout> viewWorkoutAvailability(final String activity) {
        lock.readLock().lock();
        try {
            final List<String> centres = centreDao.onboardedCentres().values().stream()
                    .map(Centre::getName)
                    .toList();

            return centres.stream()
                    .map(centre -> workoutDao.centreWorkouts(centre))
                    .flatMap(List::stream)
                    .filter(workout -> workout.getActivity().equals(activity))
                    .sorted(Comparator.comparingInt(o -> o.getTiming().getStartTime()))
                    .toList();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void bookSession(final String customerName, final String centreName, final String activity, final int startTime, final int endTime) {
        lock.writeLock().lock();
        try {
            final Workout workout = workoutDao.centreWorkouts(centreName).stream()
                    .filter(workout1 -> workout1.getActivity().equals(activity))
                    .filter(workout1 -> workout1.getAvailableSlots() > 0)
                    .filter(workout1 -> workout1.getTiming().getStartTime() <= startTime && workout1.getTiming().getEndTime() >= endTime)
                    .findFirst()
                    .orElseThrow(() -> new BookingNotAllowedException("Booking not allowed"));
            workoutDao.decrementAvailableSlots(centreName, activity);
            final Booking booking = new Booking(customerName, centreName, activity, new Timing(startTime, endTime));
            final List<Booking> bookings = Optional.ofNullable(bookingDao.getBookedDetails(customerName))
                    .orElse(new ArrayList<>());
            bookings.add(booking);
            bookingDao.addBooking(customerName, bookings);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void cancelSession(final String customerName, final String centreName, final String activity, final int startTime, final int endTime) {
        try {
            lock.writeLock().lock();
            final List<Booking> bookings = Optional.ofNullable(bookingDao.getBookedDetails(customerName))
                    .filter(bookingList -> !bookingList.isEmpty())
                    .orElseThrow(() -> new BookingDoesNotExistException("No bookings found"));

            final Booking booking = bookings.stream()
                    .filter(booking1 -> booking1.getCentreName().equals(centreName))
                    .filter(booking1 -> booking1.getActivity().equals(activity))
                    .findFirst()
                    .orElseThrow(() -> new BookingDoesNotExistException("No bookings found"));

            workoutDao.centreWorkouts(centreName).stream()
                    .filter(workout -> workout.getActivity().equals(activity))
                    .filter(workout -> workout.getTiming().getStartTime() == startTime)
                    .filter(workout -> workout.getTiming().getEndTime() == endTime)
                    .findFirst()
                    .ifPresentOrElse(workout -> {
                        workoutDao.incrementAvailableSlots(centreName, activity);
                        bookingDao.removeBooking(customerName, booking);
                    }, () -> {
                        throw new BookingDoesNotExistException("No bookings found");
                    });
        } finally {
            lock.writeLock().unlock();
        }
    }
}