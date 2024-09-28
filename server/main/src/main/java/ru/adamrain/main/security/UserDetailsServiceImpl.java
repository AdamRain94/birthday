package ru.adamrain.main.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.adamrain.main.entity.User;
import ru.adamrain.main.exception.UserTelNotFoundExcepion; // Исключение, выбрасываемое при отсутствии пользователя.
import ru.adamrain.main.repository.UserRepository; // Репозиторий для работы с пользователями.

@Service
@RequiredArgsConstructor
// Реализация UserDetailsService для загрузки деталей пользователя по номеру телефона.
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository; // Репозиторий для работы с сущностью User.

    @Override
    public UserDetails loadUserByUsername(String tel) throws UsernameNotFoundException {
        // Находим пользователя по номеру телефона, если не найден, выбрасываем исключение.
        User user = userRepository.findByTel(tel).orElseThrow(() ->
                new UserTelNotFoundExcepion("User not found. Tel is: " + tel)); // Создаем исключение с сообщением о том, что пользователь не найден.

        return new AppUserDetails(user); // Возвращаем объект AppUserDetails с данными пользователя.
    }
}
