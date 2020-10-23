package org.innopolis.kuzymvas.secondexam.secuirty;

import org.innopolis.kuzymvas.secondexam.dao.UserDAO;
import org.innopolis.kuzymvas.secondexam.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;

public class JPAAuthenticationProviderTest {

    @InjectMocks
    JPAAuthenticationProvider testedProvider;

    @Mock
    UsernamePasswordAuthenticationToken mockToken;

    @Mock
    UserRepository mockRepository;

    @Mock
    PasswordEncoder mockEncoder;

    UserDAO userInDb;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockToken.getName()).thenReturn("TestLogin");
        Mockito.when(mockToken.getCredentials()).thenReturn("MockPassword");
        userInDb = new UserDAO();
    }

    @Test
    public void authenticatePositive() {
        Mockito.when(mockRepository.findByName((String)mockToken.getName())).thenReturn(Optional.of(userInDb));
        Mockito.when(mockEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        final Authentication authenticate = testedProvider.authenticate(mockToken);
        Assert.assertEquals(mockToken.getName(),authenticate.getPrincipal());
        Assert.assertEquals(mockToken.getCredentials(),authenticate.getCredentials());
    }

    @Test
    public void authenticateNoUser() {
        Mockito.when(mockRepository.findByName((String)mockToken.getName())).thenReturn(Optional.empty());
        Mockito.when(mockEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        final Authentication authenticate = testedProvider.authenticate(mockToken);
        Assert.assertNull(authenticate);
    }

    @Test
    public void authenticateWrongPassword() {
        Mockito.when(mockRepository.findByName((String)mockToken.getName())).thenReturn(Optional.of(userInDb));
        Mockito.when(mockEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);
        final Authentication authenticate = testedProvider.authenticate(mockToken);
        Assert.assertNull(authenticate);
    }
}