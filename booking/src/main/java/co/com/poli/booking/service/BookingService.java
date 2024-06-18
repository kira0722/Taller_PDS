package co.com.poli.booking.service;

import co.com.poli.booking.persistence.entity.Booking;
import co.com.poli.booking.service.dto.BookingDetalleInDTO;
import co.com.poli.booking.service.dto.BookingInDTO;

import java.util.List;

public interface BookingService {
    Booking save(BookingInDTO bookingsInDTO);

    List<BookingDetalleInDTO> findAll();

    BookingDetalleInDTO findById(Long id);

    List<BookingDetalleInDTO> findByIdUser(Long id);

    Boolean delete(Long id);

    Boolean validarMovieRegistrada(Long id);

    Boolean validarUserRegistrado(Long id);
}
