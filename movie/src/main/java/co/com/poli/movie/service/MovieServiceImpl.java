package co.com.poli.movie.service;


import co.com.poli.movie.Feign.BookingClient;
import co.com.poli.movie.Feign.ShowtimeClient;
import co.com.poli.movie.model.Showtime;
import co.com.poli.movie.persistence.entity.Movie;
import co.com.poli.movie.persistence.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BookingClient bookingClient;

    @Autowired
    private ShowtimeClient showtimeClient;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(Long movieId) {
        // Verificar si existen programaciones asociadas a la película
        Boolean hasShowtimes = showtimeClient.existsByMovieId(movieId);
        if (hasShowtimes) {
            throw new RuntimeException("No se puede eliminar la película porque tiene programaciones asociadas");
        }

        // Verificar si existen reservas asociadas a la película
        Boolean hasBookings = bookingClient.existsByMovieId(movieId);
        if (hasBookings) {
            throw new RuntimeException("No se puede eliminar la película porque tiene reservas asociadas");
        }

        movieRepository.deleteById(movieId);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }


    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new ExpressionException("Movie not found"));
    }

}
