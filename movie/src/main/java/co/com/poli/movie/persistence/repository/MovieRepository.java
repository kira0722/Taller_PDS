package co.com.poli.movie.persistence.repository;

import co.com.poli.movie.persistence.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
