package co.com.poli.users.service;

import co.com.poli.users.persistence.entity.User;
import co.com.poli.users.service.DTO.userDTO;

import java.util.List;

public interface UserService {
    void save(User user);
    void delete(User user);
    List<User> findAll();
    User findById(Long id);
}
