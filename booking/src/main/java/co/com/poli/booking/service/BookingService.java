package co.com.poli.booking.service;

import co.com.poli.booking.persistence.entity.Booking;
import co.com.poli.booking.persistence.entity.BookingDetails;

import java.util.List;

public interface BookingService {
    Booking createBooking(Long bookingId,Long userId, Long showtimeId, Long movieId);
    List<BookingDetails> getAllBookings();
    BookingDetails getBookingsDetails(Long id);
    void deleteBooking (Long id);
    List<Booking> getBookingsByUserId(Long id);
    Boolean existsByMovieId(Long movieId);
    Boolean existsByUserId(Long userId);
}
