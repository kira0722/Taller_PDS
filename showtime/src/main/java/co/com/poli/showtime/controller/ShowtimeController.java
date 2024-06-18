package co.com.poli.showtime.controller;

import co.com.poli.showtime.helper.ResponseBuild;
import co.com.poli.showtime.persistence.entity.Showtime;
import co.com.poli.showtime.persistence.entity.ShowtimeDetails;
import co.com.poli.showtime.service.DTO.ShowtimeDTO;
import co.com.poli.showtime.service.ShowtimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    private final ResponseBuild responseBuild;

    private Showtime convertToEntity(ShowtimeDTO showtimeDTO) {
        Showtime showtime = new Showtime();
        showtime.setDate(showtimeDTO.getDate());
        showtime.setMovieId(showtimeDTO.getMovieId());
        return showtime;
    }

    @PostMapping
    public ResponseEntity<Showtime> createShowtime(@Valid @RequestBody ShowtimeDTO showtimeDTO) {
        Showtime showtime = convertToEntity(showtimeDTO);
        Showtime createdShowtime = showtimeService.createShowtime(showtime);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShowtime);
    }

    @GetMapping("/details")
    public ResponseEntity<List<ShowtimeDetails>> getAllShowtimesWithMovies() {
        List<ShowtimeDetails> showtimesWithMovies = showtimeService.getAllShowtimesWithMovies();
        return ResponseEntity.ok(showtimesWithMovies);
    }


    @GetMapping("{id}")
    public ResponseEntity<ShowtimeDetails> getShowtimeDetails(@PathVariable("id") Long id) {
        ShowtimeDetails showtimeDetails = showtimeService.getShowtimeDetails(id);
        return ResponseEntity.ok(showtimeDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable("id") Long id, @RequestBody Showtime updatedShowtime) {
        Showtime updated = showtimeService.updateShowtime(id, updatedShowtime);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/existsByMovieId")
    public ResponseEntity<Boolean> existsByMovieId(@RequestParam Long movieId) {
        boolean exists = showtimeService.existsByMovieId(movieId);
        return ResponseEntity.ok(exists);
    }


}
