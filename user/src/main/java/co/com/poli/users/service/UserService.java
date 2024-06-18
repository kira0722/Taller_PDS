package co.com.poli.users.service;

import co.com.poli.users.persistence.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserService {
    void save(User user);
    List<User> findAll();
    Optional<User> getUserById(Long id);
    void deleteById(Long id);
}
