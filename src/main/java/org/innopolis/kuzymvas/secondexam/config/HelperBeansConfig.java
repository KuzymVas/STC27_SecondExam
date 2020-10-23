package org.innopolis.kuzymvas.secondexam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Класс конфигурации для вспомогательных bean-ов, позволяющий избежать циклических зависимостей
 */
@Configuration
public class HelperBeansConfig {
    /**
     * Шифровальщик паролей
     * @return - шифровальщик BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
