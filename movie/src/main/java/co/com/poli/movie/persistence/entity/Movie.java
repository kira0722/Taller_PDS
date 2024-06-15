package co.com.poli.movie.persistence.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tittle")
    @NotEmpty(message = "tienes que agregar un titulo")
    private String title;

    @NotEmpty(message = "tienes que agregar un director")
    @Column(name = "director")
    private String director;

    @Min(value = 1, message = "la calificacion minima debe de ser 1")
    @Max(value = 5, message = "la calificacion maxima debe ser de 5")
    @Column(name = "rating")
    private int rating;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
