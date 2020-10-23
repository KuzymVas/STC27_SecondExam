package org.innopolis.kuzymvas.secondexam.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Данные для регистрации нового пользователя, полученные из JSON: имя, пароль и его подтверждение.
 */
@Getter
@Setter
@NoArgsConstructor
public class RegistrationDTO {
    String name;
    String password;
    String passwordConfirmation;

    @Override
    public String toString() {
        return "RegistrationDTO{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirmation='" + passwordConfirmation + '\'' +
                '}';
    }
}
