package ru.adamrain.main.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data //Автоматически генерирует стандартные методы, такие как getters, setters, toString(), equals(), и hashCode().
@NoArgsConstructor
@AllArgsConstructor
@Builder //Позволяет использовать шаблон проектирования "Строитель"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20, message = "Имя не должно превышать 20 символов")
    private String name;

    @Size(max = 20, message = "Фамилия не должна превышать 20 символов")
    private String fam;

    @Size(max = 20, message = "Отчество не должно превышать 20 символов")
    private String otch;

    @Min(1)
    @Max(2)
    private Integer sex;

    @Past(message = "Дата рождения должна быть в прошлом")
    private Date dateOfBirth;

    @Column(unique = true)
    private String tel;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Date dateRegistration;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)  //FetchType.EAGER указывает, что связанные данные должны загружаться немедленно, когда загружается основной объект.
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name="roles", nullable = false)
    @Builder.Default
    private Set<RoleType> roles = new HashSet<>();

    private String photo;
}
