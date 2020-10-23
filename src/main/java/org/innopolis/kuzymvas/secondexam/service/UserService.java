package org.innopolis.kuzymvas.secondexam.service;

import org.innopolis.kuzymvas.secondexam.dao.UserDAO;
import org.innopolis.kuzymvas.secondexam.dto.RegistrationDTO;
import org.innopolis.kuzymvas.secondexam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private  final UserRepository repository;
    private  final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

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
}
