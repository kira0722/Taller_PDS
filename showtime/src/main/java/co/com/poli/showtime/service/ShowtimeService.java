package co.com.poli.showtime.service;

import co.com.poli.showtime.persistence.entity.Showtime;
import co.com.poli.showtime.persistence.entity.ShowtimeDetails;

import java.util.List;

public interface ShowtimeService {
    Showtime createShowtime(Showtime showtime);
    ShowtimeDetails getShowtimeDetails(Long id);
    List<ShowtimeDetails> getAllShowtimesWithMovies();
    Showtime updateShowtime(Long showtimeId, Showtime updatedShowtime);
    boolean existsByMovieId(Long movieId);
}
