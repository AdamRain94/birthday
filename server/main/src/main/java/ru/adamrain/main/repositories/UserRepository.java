package ru.adamrain.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.adamrain.main.entitys.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByTel(String tel);
}