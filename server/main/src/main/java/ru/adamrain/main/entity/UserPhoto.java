package ru.adamrain.main.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne // Указывает на связь многие-к-одному
    @JoinColumn(name = "user_id", nullable = false, unique = true) // Внешний ключ с ограничением на уникальность
    @JsonBackReference
    private User user;

    private String path;
}
