package co.com.poli.booking.service.dto;

import lombok.Data;

@Data
public class BookingDTO {
    private Long bookingId;
    private Long userId;
    private Long showtimeId;
    private Long movieId;
}
