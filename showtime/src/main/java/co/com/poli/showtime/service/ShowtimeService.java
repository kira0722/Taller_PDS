package co.com.poli.showtime.service;

import co.com.poli.showtime.persistence.entity.Showtime;

import java.util.List;

public interface ShowtimeService {

    void save(Showtime showtime);
    void delete(Showtime showtime);
    List<Showtime> findAll();
    Showtime findById(Long id);
}
