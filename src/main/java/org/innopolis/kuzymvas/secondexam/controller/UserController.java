package org.innopolis.kuzymvas.secondexam.controller;

import org.innopolis.kuzymvas.secondexam.dto.RegistrationDTO;
import org.innopolis.kuzymvas.secondexam.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegistrationDTO dto) {
        logger.debug("New registration request: " + dto.toString());
        service.register(dto);
    }
}
