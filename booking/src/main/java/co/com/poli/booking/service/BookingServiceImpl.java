package co.com.poli.booking.service;

import co.com.poli.booking.clientFeign.MovieClient;
import co.com.poli.booking.clientFeign.ShowtimeClient;
import co.com.poli.booking.clientFeign.UserClient;
import co.com.poli.booking.model.Movie;
import co.com.poli.booking.model.Showtime;
import co.com.poli.booking.model.User;
import co.com.poli.booking.persistence.entity.Booking;
import co.com.poli.booking.persistence.entity.BookingItem;
import co.com.poli.booking.persistence.repository.BookingItemRepository;
import co.com.poli.booking.persistence.repository.BookingRepository;
import co.com.poli.booking.service.dto.BookingDetalleInDTO;
import co.com.poli.booking.service.dto.BookingInDTO;
import co.com.poli.booking.service.dto.BookingItemInDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;

    private final BookingItemRepository bookingItemRepository;

    private final MovieClient movieClient;

    private final ShowtimeClient showtimeClient;

    private final UserClient userClient;


    @Override
    /**
     * return Bookings
     * Cuando el id del Bookings es:
     *  userID o showtimesID = -1L : El servicio esta ciado
     *  bookingsID = -2L : Esta caido el servicio de movies
     *  userID o showtimesID = -3L : No existe un registro con el userId o showtimeID
     *  bookingsID = -4L : Alguna de las movie no existe
     *
     */
    public Booking save(BookingInDTO bookingInDTO) {
        Boolean servicioCaido = false;
        Boolean notFound = false;
        Booking booking = new Booking();
        booking.setShowtimeID(bookingInDTO.getShowtimeId());
        booking.setUserID(bookingInDTO.getUserId());

        Booking bookingRespuesta = new Booking();

        int users = userClient.findById(bookingInDTO.getUserId()).getCode();
        if(users == 404){
            bookingRespuesta.setId(-3L);
            bookingRespuesta.setUserID(-3L);
            notFound = true;
        }else if(users == 503){
            bookingRespuesta.setId(-1L);
            bookingRespuesta.setUserID(-1L);
            servicioCaido = true;
            notFound = true;
        }

        int showtime = showtimeClient.findById(bookingInDTO.getShowtimeId()).getCode();
        if(showtime == 404){
            bookingRespuesta.setId(-3L);
            bookingRespuesta.setShowtimeID(-3L);
        }else if(showtime == 503){
            bookingRespuesta.setId(-1L);
            bookingRespuesta.setShowtimeID(-1L);
            servicioCaido = true;
        }

        int validarServicioMovie = movieClient.findById(1L).getCode();
        if(validarServicioMovie == 503){
            bookingRespuesta.setId(-2L);
            servicioCaido = true;
        }

        if(!servicioCaido && !notFound){
            List<BookingItem> bookingsItems = new ArrayList<>();
            List<BookingItem> movieInexistente = new ArrayList<>();
            for(BookingItemInDTO bookingItem: bookingInDTO.getMovies()){
                BookingItem nuevoItem = new BookingItem();
                nuevoItem.setIdMovie(bookingItem.getIdMovie());
                bookingsItems.add(nuevoItem);
                int movie = movieClient.findById(bookingItem.getIdMovie()).getCode();
                if(movie == 404){
                    movieInexistente.add(nuevoItem);
                }
            }

            booking.setMovies(bookingsItems);

            if(movieInexistente.isEmpty()){
                return this.bookingRepository.save(booking);
            }else{
                bookingRespuesta.setId(-4L);
                bookingRespuesta.setMovies(movieInexistente);
            }
        }


        return  bookingRespuesta;
    }


    /**
     *
     * Tener en cuenta:
     * Cuando hay algun servico esta caido se devolvera en la lista un BookingsDetalleInDTO con:
     *
     *
     */
    @Override
    public List<BookingDetalleInDTO> findAll() {
        Boolean servicioCaido = false;
        List<Booking> bookings = this.bookingRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();

        List<BookingDetalleInDTO> detalleInDTOList = new ArrayList<>();

        BookingDetalleInDTO bookingRespuesta = new BookingDetalleInDTO();

        int users = userClient.findById(1L).getCode();
        if(users == 503){
            bookingRespuesta.setId(-1L);
            User user = new User();
            user.setId(-1L);
            bookingRespuesta.setUser(user);
            servicioCaido = true;
        }

        int showtime = showtimeClient.findById(1L).getCode();
        if(showtime == 503){
            bookingRespuesta.setId(-1L);
            Showtime showtimes = new Showtime();
            showtimes.setId(-1L);
            bookingRespuesta.setShowtimes(showtimes);
            servicioCaido = true;
        }

        int validarServicioMovie = movieClient.findById(1L).getCode();
        if(validarServicioMovie == 503){
            bookingRespuesta.setId(-2L);
            servicioCaido = true;
        }

        if(!servicioCaido){
            for(int i = 0; i < bookings.size(); i++){
                BookingDetalleInDTO bookingDetalleInDTO = new BookingDetalleInDTO();

                bookingDetalleInDTO.setId(bookings.get(i).getId());

                User user = modelMapper.map(userClient.findById(bookings.get(i).getUserID()).getData(),User.class);
                bookingDetalleInDTO.setUser(user);

                Showtime showtimes = modelMapper.map(showtimeClient.findById(bookings.get(i).getShowtimeID()).getData(),Showtime.class);
                bookingDetalleInDTO.setShowtimes(showtimes);

                List<Movie> movies = bookings.get(i).getMovies().stream()
                        .map(showtimesItem -> {
                            Movie movie = modelMapper.map(movieClient.findById(showtimesItem.getIdMovie()).getData(),Movie.class);
                            return movie;
                        }).collect(Collectors.toList());

                bookingDetalleInDTO.setMovies(movies);

                detalleInDTOList.add(bookingDetalleInDTO);
            }
        }else{
            detalleInDTOList.add(bookingRespuesta);
        }


        return detalleInDTOList;
    }

    @Override
    public BookingDetalleInDTO findById(Long id) {
        Boolean servicioCaido = false;
        Optional<Booking> bookings = this.bookingRepository.findById(id);

        if(!bookings.isEmpty()){
            BookingDetalleInDTO bookingsRespuesta = new BookingDetalleInDTO();

            int users = userClient.findById(1L).getCode();
            if(users == 503){
                bookingsRespuesta.setId(-1L);
                User user = new User();
                user.setId(-1L);
                bookingsRespuesta.setUser(user);
                servicioCaido = true;
            }

            int showtime = showtimeClient.findById(1L).getCode();
            if(showtime == 503){
                bookingsRespuesta.setId(-1L);
                Showtime showtimes = new Showtime();
                showtimes.setId(-1L);
                bookingsRespuesta.setShowtimes(showtimes);
                servicioCaido = true;
            }

            int validarServicioMovie = movieClient.findById(1L).getCode();
            if(validarServicioMovie == 503){
                bookingsRespuesta.setId(-2L);
                servicioCaido = true;
            }


            if(!servicioCaido){
                ModelMapper modelMapper = new ModelMapper();

                BookingDetalleInDTO bookingsDetalleInDTO = new BookingDetalleInDTO();
                bookingsDetalleInDTO.setId(bookings.get().getId());

                User user = modelMapper.map(userClient.findById(bookings.get().getUserID()).getData(),User.class);
                bookingsDetalleInDTO.setUser(user);

                Showtime showtimes = modelMapper.map(showtimeClient.findById(bookings.get().getShowtimeID()).getData(),Showtime.class);
                bookingsDetalleInDTO.setShowtimes(showtimes);

                List<Movie> movies = bookings.get().getMovies().stream()
                        .map(showtimeItem -> {
                            Movie movie = modelMapper.map(movieClient.findById(showtimeItem.getIdMovie()).getData(),Movie.class);
                            return movie;
                        }).collect(Collectors.toList());

                bookingsDetalleInDTO.setMovies(movies);

                return bookingsDetalleInDTO;
            }

            return bookingsRespuesta;

        }

        return null;
    }

    @Override
    public List<BookingDetalleInDTO> findByIdUser(Long id) {
        List<Booking> bookings = this.bookingRepository.findByUserID(id);
        List<BookingDetalleInDTO> detalleInDTOList = new ArrayList<>();
        if(bookings != null){
            for(Booking booking: bookings){
                detalleInDTOList.add(findById(booking.getId()));
            }
            return detalleInDTOList;
        }

        return null;
    }

    @Override
    public Boolean delete(Long id) {
        Optional<Booking> bookings = this.bookingRepository.findById(id);

        if (!bookings.isEmpty()) {
            this.bookingRepository.delete(bookings.get());
            return true;
        }

        return false;
    }

    @Override
    public Boolean validarMovieRegistrada(Long id) {
        List<BookingItem>  bookingsItem = this.bookingItemRepository.findByIdMovie(id);

        if(!bookingsItem.isEmpty()){
            return true;
        }

        return false;
    }

    @Override
    public Boolean validarUserRegistrado(Long id) {
        List<Booking> bookings = this.bookingRepository.findByUserID(id);

        if(!bookings.isEmpty()){
            return true;
        }

        return false;
    }

}
