package co.com.poli.showtime.service.DTO;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ShowtimeDTO {
    private LocalDateTime date;
    private Long movieId;
}
