package org.innopolis.kuzymvas.secondexam.service;

import org.innopolis.kuzymvas.secondexam.dao.UserDAO;
import org.innopolis.kuzymvas.secondexam.dto.RegistrationDTO;
import org.innopolis.kuzymvas.secondexam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис пользователей: предоставляет возможности регистрации и вывода содержимого таблицы пользователей
 */
@Service
public class UserService {

    private  final UserRepository repository;
    private  final PasswordEncoder encoder;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(
            UserRepository repository, PasswordEncoder encoder, JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.encoder = encoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     *  Регистрирует нового пользователя
     *
     * @param dto - данные для регистрации: имя, пароль и его подтверждение
     */
    public void register(RegistrationDTO dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
            throw new IllegalArgumentException("Can't register. Passwords mismatch");
        }
        final Optional<UserDAO> existingUser = repository.findByName(dto.getName());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Can't register. User already exists");
        }
        UserDAO newUser = new UserDAO();
        newUser.setName(dto.getName());
        newUser.setPassword(encoder.encode(dto.getPassword()));
        repository.save(newUser);
    }

    /**
     * Получает доступ ко всем строкам таблицы пользователей через JDBC
     * @return - все строки таблицы пользователей
     */
    public List<UserDAO> getAllRows() {
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM users");
        final List<UserDAO> daos = new ArrayList<>();
        rows.stream()
                .map(this::parseRow)
                .collect(Collectors.toCollection(() -> daos));
        return daos;
    }

    /**
     *  Получает доступ к одной строке таблицы пользователей через JDBC
     * @param id - идентификатор строки
     * @return - строку таблицы пользователей
     */
    public UserDAO getSingleRow(Integer id) {
        final Map<String, Object> row = DataAccessUtils.singleResult(
                jdbcTemplate.queryForList("SELECT * FROM users WHERE id = ?", id));
        if (row == null) {
            throw new IllegalArgumentException("User with given id do not exist");
        }
        return parseRow(row);
    }

    /**
     * Преобразует таблицу, возвращаемую JDBC в объект описания пользователя
     * @param row - Таблица (map), возвращаемая JDBC
     * @return - описание пользователя
     */
    private UserDAO parseRow(Map<String, Object> row) {
        final UserDAO dao = new UserDAO();
        dao.setId((Integer) row.get("id"));
        dao.setName((String) row.get("name"));
        dao.setPassword((String) row.get("password"));
        return dao;
    }
}
