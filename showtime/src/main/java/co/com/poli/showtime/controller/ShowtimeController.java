package co.com.poli.showtime.controller;

import co.com.poli.showtime.helper.Response;
import co.com.poli.showtime.helper.ResponseBuild;
import co.com.poli.showtime.persistence.entity.Showtime;
import co.com.poli.showtime.service.DTO.ShowtimeDTO;
import co.com.poli.showtime.service.ShowtimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {
    private final ShowtimeService service;
    private final ResponseBuild build;

    private Showtime convertToEntity(ShowtimeDTO showtimeDTO) {
        Showtime showtime = new Showtime();
        showtime.setDate(showtimeDTO.getDate());
        return showtime;
    }

    @PostMapping
    public Response save(@Valid @RequestBody ShowtimeDTO showtimeDTO, BindingResult result){
        if(result.hasErrors()){
            return build.success(format(result));
        }
        Showtime showtime = convertToEntity(showtimeDTO);
        service.save(showtime);
        return build.success(showtime);
    }

    @GetMapping
    public Response findAll(){
        return build.success(service.findAll());
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable Long id){
        Showtime showtime = service.findById(id);
        if(showtime == null){
            return build.success("el usuario no existe");
        }
        return build.success(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable("id") Long id){
        Showtime showtime = (Showtime) service.findById(id);
        if(showtime==null){
            return build.success("El usuario a eliminar no existe");
        }
        service.delete(showtime);
        return build.success(showtime);
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
