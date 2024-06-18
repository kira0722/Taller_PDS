package co.com.poli.movie.feign;

import co.com.poli.movie.model.Showtime;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "showtime")
public interface ShowtimeClient {
    @GetMapping("api/v1/poli/showtime/{movieId}")
    List<Showtime> getShowtimesByMovieId(@PathVariable("movieId") Long movieId);
}
