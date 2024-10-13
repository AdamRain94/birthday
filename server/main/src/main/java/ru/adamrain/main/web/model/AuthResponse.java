package ru.adamrain.main.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private Long id;
    private String token;
    private String name;
    private String fam;
    private String otch;
    private String tel;
    private String photo;
    private String refreshToken;
    private Date dateOfBirth;
    private Date dateRegistration;
}
