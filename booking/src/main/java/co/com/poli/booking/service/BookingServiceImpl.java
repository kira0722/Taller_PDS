package co.com.poli.booking.service;

import co.com.poli.booking.clientFeign.MovieClient;
import co.com.poli.booking.clientFeign.ShowtimeClient;
import co.com.poli.booking.clientFeign.UserClient;
import co.com.poli.booking.model.Movie;
import co.com.poli.booking.model.Showtime;
import co.com.poli.booking.model.ShowtimeDetails;
import co.com.poli.booking.model.User;
import co.com.poli.booking.persistence.entity.Booking;
import co.com.poli.booking.persistence.entity.BookingDetails;
import co.com.poli.booking.persistence.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;

    @Autowired
    private  MovieClient movieClient;

    @Autowired
    private  ShowtimeClient showtimeClient;

    @Autowired
    private UserClient userClient;

    public Booking createBooking(Long bookingId,Long userId, Long showtimeId, Long movieId){
        User user = userClient.getUserById(userId);
        if (user == null){
            throw new RuntimeException("no se encontro un usuario");
        }

        ShowtimeDetails showtime = showtimeClient.getShowtimeDetails(showtimeId);
        if (showtime == null) {
            throw new RuntimeException("Showtime not found");
        }

        // Validar Movie
        Movie movie = movieClient.getMovieById(movieId);
        if (movie == null) {
            throw new RuntimeException("Movie not found");
        }

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setShowtimeId(showtimeId);
        booking.setMovieId(movieId);
        return bookingRepository.save(booking);


    }

    @Override
    public BookingDetails getBookingsDetails(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no se encontro un booking"));
        Movie movie = movieClient.getMovieById(booking.getId());
        User user = userClient.getUserById(booking.getId());
        ShowtimeDetails showtimeDetails = showtimeClient.getShowtimeDetails(booking.getId());

        return new BookingDetails(booking.getId(), booking.getUserId(), booking.getShowtimeId(), booking.getMovieId(), movie.getTitle(), movie.getDirector(), movie.getRating());
    }

    @Override
    public List<BookingDetails> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(booking ->{
                    Movie movie = movieClient.getMovieById(booking.getId());
                    User user = userClient.getUserById(booking.getId());
                    ShowtimeDetails showtimeDetails = showtimeClient.getShowtimeDetails(booking.getId());
                    return new BookingDetails(booking.getId(), booking.getUserId(), booking.getShowtimeId(), booking.getMovieId(), movie.getTitle(), movie.getDirector(), movie.getRating());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public Boolean existsByMovieId(Long movieId) {
        return bookingRepository.existsByMovieId(movieId);
    }

    @Override
    public Boolean existsByUserId(Long userId) {
        return bookingRepository.existsByUserId(userId);
    }
}
