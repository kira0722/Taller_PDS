package co.com.poli.booking.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class BookingDetails {
    private Long bookingId;
    private Long userId;
    private Long showtimeId;
    private Long movieId;
    private String title;
    private String director;
    private Double rating;

    public BookingDetails(Long bookingId,Long userId, Long showtimeId, Long movieId, String title, String director, Double rating){
        this.bookingId = bookingId;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.rating = rating;
    }


}
