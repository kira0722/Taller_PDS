package co.com.poli.users.controller;


import co.com.poli.users.helper.Response;
import co.com.poli.users.helper.ResponseBuild;
import co.com.poli.users.persistence.entity.User;
import co.com.poli.users.service.DTO.userDTO;
import co.com.poli.users.service.UserService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseBuild build;


    private User convertToEntity(userDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLast_name());
        return user;
    }



    @PostMapping
    public Response save(@Valid @RequestBody userDTO userdto, BindingResult result){
        if(result.hasErrors()){
            return build.success(format(result));
        }
        User user = convertToEntity(userdto);
        userService.save(user);
        return build.success(user);
    }

    @GetMapping
    public Response findAll(){
        return build.success(userService.findAll());
    }


    @DeleteMapping("/{id}")
    public Response delete(@PathVariable("id") Long id){
        User user = (User) userService.findById(id);
        if(user==null){
            return build.success("El usuario a eliminar no existe");
        }
        userService.delete(user);
        return build.success(user);
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
