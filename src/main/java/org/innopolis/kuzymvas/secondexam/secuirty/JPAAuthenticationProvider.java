package org.innopolis.kuzymvas.secondexam.secuirty;

import org.innopolis.kuzymvas.secondexam.controller.TableAccessController;
import org.innopolis.kuzymvas.secondexam.dao.UserDAO;
import org.innopolis.kuzymvas.secondexam.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class JPAAuthenticationProvider implements AuthenticationProvider {


    private final Logger logger = LoggerFactory.getLogger(JPAAuthenticationProvider.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public JPAAuthenticationProvider(
            UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(
            Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        logger.debug("Request for authentication -  name: " + name + ", password - " + password);
        Optional<UserDAO> user = userRepository.findByName(name);
        if (user.isPresent()) {
            if (encoder.matches(password, user.get().getPassword())) {
                logger.debug("Success");
                return new UsernamePasswordAuthenticationToken(
                        name, password, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
            }
        }
        logger.debug("Denied");
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
