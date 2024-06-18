package co.com.poli.showtime.service;

import co.com.poli.showtime.clientFeign.MovieClient;
import co.com.poli.showtime.model.Movie;
import co.com.poli.showtime.persistence.entity.ShowtimeDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.poli.showtime.persistence.entity.Showtime;
import co.com.poli.showtime.persistence.repository.ShowtimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService{
    @Autowired
    private MovieClient movieClient;
    @Autowired
    private  ShowtimeRepository showtimeRepository;

    @Override
    public Showtime createShowtime(Showtime showtime) {
        movieClient.getMovieById(showtime.getMovieId());
        return showtimeRepository.save(showtime);
    }

    public List<ShowtimeDetails> getAllShowtimesWithMovies() {
        List<Showtime> showtimes = showtimeRepository.findAll();
        return showtimes.stream()
                .map(showtime -> {
                    Movie movie = movieClient.getMovieById(showtime.getMovieId());
                    return new ShowtimeDetails(showtime.getId(), showtime.getDate(), movie.getId(), movie.getTitle(), movie.getDirector(), movie.getRating());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Showtime updateShowtime(Long showtimeId, Showtime updatedShowtime) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new EntityNotFoundException("Showtime not found with id: " + showtimeId));

        showtime.setDate(updatedShowtime.getDate());
        showtime.setMovieId(updatedShowtime.getMovieId());
        // Puedes actualizar otros campos de Showtime aquí según sea necesario

        if (updatedShowtime.getMovieId() != null) {
            showtime.setMovieId(updatedShowtime.getMovieId());
        }

        return showtimeRepository.save(showtime);
    }

    @Override
    public ShowtimeDetails getShowtimeDetails(Long id) {
        Showtime showtime = showtimeRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("no encontrado"));
        Movie movie = movieClient.getMovieById(showtime.getMovieId());
        return new ShowtimeDetails(showtime.getId(), showtime.getDate(), movie.getId(), movie.getTitle(), movie.getDirector(), movie.getRating());
    }

    @Override
    public boolean existsByMovieId(Long movieId) {
        return showtimeRepository.existsByMovieId(movieId);
    }
}
