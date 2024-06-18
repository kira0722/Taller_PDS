package co.com.poli.booking.clientFeign;

import co.com.poli.booking.model.Showtime;
import co.com.poli.booking.model.ShowtimeDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "showtime")
public interface ShowtimeClient {
    @GetMapping("/api/v1/poli/showtimes/{id}")
    ShowtimeDetails getShowtimeDetails(@PathVariable("id") Long id);

    @GetMapping("{/api/v1/poli/showtimes/id}")
    Showtime getShowtimeById(@PathVariable Long id);
}
