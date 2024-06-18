package co.com.poli.booking.service.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BookingInDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Long showtimeId;

    private List<BookingItemInDTO> movies;
}
