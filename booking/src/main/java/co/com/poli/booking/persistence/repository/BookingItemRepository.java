package co.com.poli.booking.persistence.repository;

import co.com.poli.booking.persistence.entity.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {
    List<BookingItem> findByIdMovie(Long id);
}
