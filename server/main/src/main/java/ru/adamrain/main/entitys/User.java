package ru.adamrain.main.entitys;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "users")
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
    private Date dateOfBirth;
    @Column(unique = true)
    private String tel;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Date dateRegistration;

    public User() {

    }

    public User(Long id, String name, String fam, String otch, Date dateOfBirth, String tel, String password, Date dateRegistration) {
        this.id = id;
        this.name = name;
        this.fam = fam;
        this.otch = otch;
        this.dateOfBirth = dateOfBirth;
        this.tel = tel;
        this.password = password;
        this.dateRegistration = dateRegistration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public String getOtch() {
        return otch;
    }

    public void setOtch(String otch) {
        this.otch = otch;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }
}
