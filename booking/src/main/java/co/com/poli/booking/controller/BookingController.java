package co.com.poli.booking.controller;


import co.com.poli.booking.helper.ResponseBuild;
import co.com.poli.booking.persistence.entity.Booking;
import co.com.poli.booking.persistence.entity.BookingDetails;
import co.com.poli.booking.service.BookingService;
import co.com.poli.booking.service.dto.BookingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking booking = bookingService.createBooking(bookingDTO.getBookingId(),bookingDTO.getUserId(), bookingDTO.getShowtimeId(), bookingDTO.getMovieId());
        return ResponseEntity.status(201).body(booking);
    }


    @GetMapping("{id}")
    public ResponseEntity<BookingDetails> getBookingsDetails(@PathVariable("id") Long id){
        BookingDetails bookingDetails = bookingService.getBookingsDetails(id);
        return ResponseEntity.ok(bookingDetails);
    }

    @GetMapping("details")
    public ResponseEntity<List<BookingDetails>> getAllBookings(){
        List<BookingDetails> bookingCentral = bookingService.getAllBookings();
        return ResponseEntity.ok(bookingCentral);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/existsByMovieId")
    public ResponseEntity<Boolean> existsByMovieId(@RequestParam Long movieId) {
        Boolean exists = bookingService.existsByMovieId(movieId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/existsByUserId")
    public ResponseEntity<Boolean> existsByUserId(@RequestParam Long userId) {
        Boolean exists = bookingService.existsByUserId(userId);
        return ResponseEntity.ok(exists);
    }
}
