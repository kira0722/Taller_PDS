package co.com.poli.movie.service.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MovieDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String director;

    @Max(5)
    @Min(1)
    private Double rating;
}
