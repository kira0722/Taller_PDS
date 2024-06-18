package co.com.poli.booking.controller;


import co.com.poli.booking.helper.Response;
import co.com.poli.booking.helper.ResponseBuild;
import co.com.poli.booking.persistence.entity.Booking;
import co.com.poli.booking.persistence.entity.BookingItem;
import co.com.poli.booking.service.BookingService;
import co.com.poli.booking.service.dto.BookingDetalleInDTO;
import co.com.poli.booking.service.dto.BookingInDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    private final ResponseBuild responseBuild;

    @PostMapping
    public Response save(@Valid @RequestBody BookingInDTO bookingInDTO, BindingResult result) {
        if(result.hasErrors()){
            return this.responseBuild.failed(format(result));
        }

        Booking booking = this.bookingService.save(bookingInDTO);

        if(booking.getId() > 0){
            return this.responseBuild.success(booking);
        }else if(booking.getId() == -1L || booking.getId() == -2L){
            String mensajeError = "Esta(n) abajo  servicio(s) de:";

            if(booking.getUserID() != null && booking.getUserID() == -1L){
                mensajeError += " users";
            }

            if(booking.getShowtimeID() != null && booking.getShowtimeID() == -1L){
                mensajeError += " showtimes";
            }

            if(booking.getId() != null && booking.getId() == -2L){
                mensajeError += " movies";
            }

            return this.responseBuild.failedServer(mensajeError);

        } else if(booking.getId() == -3L){
            String mensajeError = "";
            if(booking.getUserID() != null && booking.getUserID() == -3L){
                mensajeError += "No existe el user con id: " + bookingInDTO.getUserId() + ", ";
            }

            if(booking.getShowtimeID() != null && booking.getShowtimeID() == -3L){
                mensajeError += "No existe el showtimes con id: " + bookingInDTO.getShowtimeId() + ", ";
            }

            return this.responseBuild.failedNotFound(mensajeError);
        }else if(booking.getId() == -4L){
            String mensajeError = "La(s) movie(s): ";
            for(BookingItem bookingsItem: booking.getMovies()){
                mensajeError  += bookingsItem.getIdMovie() + ", ";
            }

            mensajeError += " no existe(n)";
            return this.responseBuild.failedNotFound(mensajeError);
        }

        return this.responseBuild.success(booking);
    }


    @GetMapping
    public Response findAll() {
        List<BookingDetalleInDTO> bookings = this.bookingService.findAll();

        if(bookings.size() > 0){
            if(bookings.get(0).getId() != null && (bookings.get(0).getId() == -1L || bookings.get(0).getId() == -2L)){
                String mensajeError = "Esta(n) abajo  servicio(s) de:";

                if(bookings.get(0).getUser() != null && bookings.get(0).getUser().getId() != null && bookings.get(0).getUser().getId() == -1L){
                    mensajeError += " users";
                }

                if(bookings.get(0).getShowtimes() != null && bookings.get(0).getShowtimes().getId() != null && bookings.get(0).getShowtimes().getId() == -1L){
                    mensajeError += " showtimes";
                }

                if(bookings.get(0).getId()  != null && bookings.get(0).getId() == -2L ){
                    mensajeError += " movies";
                }

                return this.responseBuild.failedServer(mensajeError);
            }

            return this.responseBuild.success(bookings);
        }

        return this.responseBuild.failedNotFound("No existen bookings registrados");
    }

    @GetMapping("/{id}")
    public Response findById(Long id) {
        BookingDetalleInDTO bookings = this.bookingService.findById(id);

        if(bookings != null){
            if(bookings.getId() != null && (bookings.getId() == -1L || bookings.getId() == -2L)){
                String mensajeError = "Esta(n) abajo  servicio(s) de:";

                if(bookings.getUser() != null && bookings.getUser().getId() != null && bookings.getUser().getId() == -1L){
                    mensajeError += " users";
                }

                if(bookings.getShowtimes() != null && bookings.getShowtimes().getId() != null && bookings.getShowtimes().getId() == -1L){
                    mensajeError += " showtimes";
                }

                if(bookings.getId()  != null && bookings.getId() == -2L ){
                    mensajeError += " movies";
                }

                return this.responseBuild.failedServer(mensajeError);
            }

            return this.responseBuild.success(bookings);
        }

        return this.responseBuild.failedNotFound("No existe un bookings para el id:" + id);
    }

    @GetMapping("/users/{id}")
    public Response findByIdUser(@PathVariable("id") Long id) {
        List<BookingDetalleInDTO> bookings = this.bookingService.findByIdUser(id);

        if(!bookings.isEmpty()){
            if(bookings.get(0).getId() != null && (bookings.get(0).getId() == -1L || bookings.get(0).getId() == -2L)){
                String mensajeError = "Esta(n) abajo  servicio(s) de:";

                if(bookings.get(0).getUser() != null && bookings.get(0).getUser().getId() != null && bookings.get(0).getUser().getId() == -1L){
                    mensajeError += " users";
                }

                if(bookings.get(0).getShowtimes() != null && bookings.get(0).getShowtimes().getId() != null && bookings.get(0).getShowtimes().getId() == -1L){
                    mensajeError += " showtimes";
                }

                if(bookings.get(0).getId()  != null && bookings.get(0).getId() == -2L ){
                    mensajeError += " movies";
                }

                return this.responseBuild.failedServer(mensajeError);
            }

            return this.responseBuild.success(bookings);
        }

        return this.responseBuild.failedNotFound("No existe un bookings asociado al user con id: " + id);
    }


    @DeleteMapping("/{id}")
    public Response delete(@PathVariable("id") Long id) {
        BookingDetalleInDTO bookings = this.bookingService.findById(id);

        if(bookings != null){
            this.bookingService.delete(bookings.getId());
            return this.responseBuild.success("El bookings con id: " + id + " fue eliminado con exito" );
        }

        return this.responseBuild.failedNotFound("No existe un bookings con id: " + id);
    }

    @GetMapping("/movie/{id}")
    public Response validarMovieRegistrada(@PathVariable("id") Long id) {
        Boolean bookingsItem = this.bookingService.validarMovieRegistrada(id);

        if(bookingsItem){
            return this.responseBuild.success("Existe bookings para la movie con id: " + id);
        }

        return this.responseBuild.failedNotFound("No existe bookings para la movie con id: " + id);
    }

    @GetMapping("/user/{id}")
    public Response validarUserRegistrado(@PathVariable("id") Long id) {
        Boolean bookings = this.bookingService.validarUserRegistrado(id);

        if(bookings){
            return this.responseBuild.success("Existe bookings para el user con id: " + id);
        }

        return this.responseBuild.failedNotFound("No existe bookings para el usuer con id: " + id);
    }



    private List<Map<String, String>> format(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(error ->{
                    Map<String, String> newError = new HashMap<>();
                    newError.put(error.getField(), error.getDefaultMessage());
                    return newError;
                }).collect(Collectors.toList());
        return errors;

    }
}
