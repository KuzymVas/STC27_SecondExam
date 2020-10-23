package org.innopolis.kuzymvas.secondexam.secuirty;

import org.innopolis.kuzymvas.secondexam.dao.UserDAO;
import org.innopolis.kuzymvas.secondexam.repository.UserRepository;
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

    UserRepository userRepository;
    PasswordEncoder encoder;


    @Override
    public Authentication authenticate(
            Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<UserDAO> user = userRepository.findByName(name);
        if (user.isPresent()) {
            if (encoder.matches(user.get().getPassword(), password)) {
                return new UsernamePasswordAuthenticationToken(
                        name, password, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
