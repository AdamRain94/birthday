package ru.adamrain.main.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.adamrain.main.entity.User;

import java.util.Collection;

@RequiredArgsConstructor
// Класс AppUserDetails реализует интерфейс UserDetails, предоставляя детали аутентификации и авторизации для Spring Security.
public class AppUserDetails implements UserDetails {

    private final User user; // Поле для хранения пользователя, данные которого используются для аутентификации.

    // Метод для получения ID пользователя.
    public Long getId(){
        return user.getId();
    }

    // Метод для получения ролей пользователя. Каждая роль преобразуется в объект SimpleGrantedAuthority.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.name())) // Преобразуем каждую роль в SimpleGrantedAuthority.
                .toList();
    }

    // Метод для получения пароля пользователя.
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Метод для получения имени пользователя (в данном случае его телефон).
    @Override
    public String getUsername() {
        return user.getTel();
    }

    // Метод для проверки, что учетная запись не истекла.
    @Override
    public boolean isAccountNonExpired() {
        return true; // В данном примере всегда возвращаем true.
    }

    // Метод для проверки, что учетная запись не заблокирована.
    @Override
    public boolean isAccountNonLocked() {
        return true; // Учетная запись всегда не заблокирована.
    }

    // Метод для проверки, что учетные данные (пароль) не истекли.
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Пароль всегда считается не истекшим.
    }

    // Метод для проверки, что учетная запись включена (активна).
    @Override
    public boolean isEnabled() {
        return true; // Учетная запись всегда активна.
    }
}
