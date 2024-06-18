package co.com.poli.showtime.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowtimeDetails {
    private Long showtimeId;
    private LocalDateTime date;
    private Long movieId;
    private String title;
    private String director;
    private Double rating;

    public ShowtimeDetails(Long showtimeId, LocalDateTime date, Long movieId, String title, String director, Double rating) {
        this.showtimeId = showtimeId;
        this.date = date;
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.rating = rating;
    }
}
