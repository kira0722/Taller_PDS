package co.com.poli.movie.model;

import co.com.poli.movie.persistence.entity.Movie;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class Showtime {

    private Long id;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @Transient
    private Movie movie;
}
