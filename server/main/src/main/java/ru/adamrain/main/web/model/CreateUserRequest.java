package ru.adamrain.main.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.adamrain.main.entity.RoleType;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

    private String name;
    private String tel;
    private String password;
}

