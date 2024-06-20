package co.com.poli.movie.Feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "showtime")
public interface ShowtimeClient {
    @GetMapping("api/v1/poli/showtimes/existsByMovieId")
    Boolean existsByMovieId(@RequestParam("movieId") Long movieId);
}
