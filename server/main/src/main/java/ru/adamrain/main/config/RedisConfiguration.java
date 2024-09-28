package ru.adamrain.main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import ru.adamrain.main.entity.RefreshToken;

import java.time.Duration;
import java.util.Collections;

@Configuration
// Аннотация @Configuration указывает, что этот класс является конфигурационным и будет использоваться Spring для настройки компонентов.
@EnableRedisRepositories(keyspaceConfiguration = RedisConfiguration.RefreshTokenKeyspaceConfiguration.class,
        enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
// Включаем поддержку Redis-репозиториев и настраиваем ключевое пространство через RefreshTokenKeyspaceConfiguration.
// enableKeyspaceEvents указывает на то, что нужно включить обработку событий для пространства ключей при запуске приложения.
public class RedisConfiguration {

    @Value("${app.jwt.refreshTokenExpiration}")
    // Внедрение значения свойства app.jwt.refreshTokenExpiration, которое определяет время жизни токенов.
    private Duration refreshTokenExpiration;

    @Bean
    // Создаём bean JedisConnectionFactory, который устанавливает соединение с Redis с использованием параметров хоста и порта.
    public JedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

        // Устанавливаем конфигурацию для одиночного Redis-сервера, задав параметры хоста и порта.
        configuration.setHostName(redisProperties.getHost());
        configuration.setPort(redisProperties.getPort());

        // Возвращаем фабрику соединений для работы с Redis.
        return new JedisConnectionFactory(configuration);
    }

    // Внутренний класс, который настраивает ключевое пространство для объектов RefreshToken.
    public class RefreshTokenKeyspaceConfiguration extends KeyspaceConfiguration {

        private static final String REFRESH_TOKEN_KEYSPACE = "refresh_tokens";
        // Указываем имя ключевого пространства для хранения токенов в Redis.

        @Override
        protected Iterable<KeyspaceSettings> initialConfiguration() {
            // Настраиваем пространство ключей для класса RefreshToken с именем "refresh_tokens".
            KeyspaceSettings keyspaceSettings = new KeyspaceSettings(RefreshToken.class, REFRESH_TOKEN_KEYSPACE);

            // Устанавливаем время жизни (TTL) для ключей на основе значения refreshTokenExpiration.
            keyspaceSettings.setTimeToLive(refreshTokenExpiration.getSeconds());

            // Возвращаем настройки пространства ключей.
            return Collections.singleton(keyspaceSettings);
        }
    }
}

