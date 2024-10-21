package ru.adamrain.main.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.adamrain.main.entity.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTel(String tel);

    Optional<User> findById(Long id);

    Boolean existsByTel(String tel);

}