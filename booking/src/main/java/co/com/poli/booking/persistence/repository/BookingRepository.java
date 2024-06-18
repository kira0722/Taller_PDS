package co.com.poli.booking.persistence.repository;

import co.com.poli.booking.persistence.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    boolean existsByMovieId(Long movieId);
    boolean existsByUserId(Long userId);
}
