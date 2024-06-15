package co.com.poli.movie.controller;


import co.com.poli.movie.helper.Response;
import co.com.poli.movie.helper.ResponseBuild;
import co.com.poli.movie.persistence.entity.Movie;
import co.com.poli.movie.service.DTO.MovieDTO;
import co.com.poli.movie.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final ResponseBuild build;

    private Movie convertToEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setDirector(movieDTO.getDirector());
        movie.setTitle(movieDTO.getTitle());
        movie.setRating(movieDTO.getRating());
        return movie;
    }

    @PostMapping
    public Response save(@Valid @RequestBody MovieDTO movieDTO, BindingResult result){
        if(result.hasErrors()){
            return build.success(format(result));
        }
        Movie movie = convertToEntity(movieDTO);
        movieService.save(movie);
        return build.success(movie);
    }

    @GetMapping
    public Response findAll(){
        return build.success(movieService.findAll());
    }


    @GetMapping("/{id}")
    public Response findById(@PathVariable Long id){
        Movie movie = movieService.findById(id);
        if(movie == null){
            return build.success("el usuario no existe");
        }
        return build.success(movieService.findById(id));
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable("id") Long id){
        Movie movie = (Movie) movieService.findById(id);
        if(movie==null){
            return build.success("El usuario a eliminar no existe");
        }
        movieService.delete(movie);
        return build.success(movie);
    }




    private List<Map<String,String>> format(BindingResult result){
        return result.getFieldErrors()
                .stream().map(error -> {
                    Map<String,String> err = new HashMap<>();
                    err.put(error.getField(),error.getDefaultMessage());
                    return err;
                }).collect(Collectors.toList());
    }

}
