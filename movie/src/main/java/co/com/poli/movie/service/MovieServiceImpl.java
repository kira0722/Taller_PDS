package co.com.poli.movie.service;


import co.com.poli.movie.feign.ShowtimeClient;
import co.com.poli.movie.helper.ResponseBuild;
import co.com.poli.movie.model.Showtime;
import co.com.poli.movie.persistence.entity.Movie;
import co.com.poli.movie.persistence.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
    private ShowtimeClient showtimeClient;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Transactional
    public void delete(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + movieId));

        // Verificar si hay showtimes asociados a esta película
        List<Showtime> showtimes = showtimeClient.getShowtimesByMovieId(movieId);
        if (!showtimes.isEmpty()) {
            throw new IllegalStateException("Cannot delete movie with id " + movieId + " because it has associated showtimes.");
        }

        // Si no hay showtimes asociados, procede con la eliminación
        movieRepository.delete(movie);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }


    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new ExpressionException("Movie not found"));
    }

}
