package co.com.poli.booking.service.dto;


import co.com.poli.booking.model.Movie;
import co.com.poli.booking.model.Showtime;
import co.com.poli.booking.model.User;
import lombok.Data;

import java.util.List;

@Data
public class BookingDetalleInDTO {
    private Long id;
    private User user;
    private Showtime showtimes;
    private List<Movie> movies;
}
