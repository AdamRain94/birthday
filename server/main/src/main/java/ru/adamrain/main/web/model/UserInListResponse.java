package ru.adamrain.main.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInListResponse {
    private Long id;
    private String name;
    private String fam;
    private String otch;
    private String tel;
    private String photo;
    private Date dateOfBirth;
}
