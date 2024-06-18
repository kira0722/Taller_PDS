package co.com.poli.movie.service;

import co.com.poli.movie.persistence.entity.Movie;
import co.com.poli.movie.service.DTO.MovieDTO;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    void save(Movie movie);
    void deleteMovie(Long movieId);
    List<Movie> findAll();
    Movie getMovieById(Long id);

}
