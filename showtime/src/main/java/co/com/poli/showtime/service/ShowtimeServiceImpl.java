package co.com.poli.showtime.service;


import co.com.poli.showtime.persistence.entity.Showtime;
import co.com.poli.showtime.persistence.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService{
    private final ShowtimeRepository showtimeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Showtime showtime) {
        showtimeRepository.save(showtime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Showtime showtime) {
        showtimeRepository.delete(showtime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Showtime> findAll() {
        return showtimeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Showtime findById(Long id) {
        return showtimeRepository.findById(id).orElse(null);
    }
}
