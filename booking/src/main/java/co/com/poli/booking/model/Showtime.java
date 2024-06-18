package co.com.poli.booking.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Showtime {
    private Long id;
    private LocalDate date;
    private Long moveId;
}
