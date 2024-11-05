package org.sysc4806g30.graduateadmissionsmanagementsystem;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sysc4806g30.graduateadmissionsmanagementsystem.loginplatform.LoginPlatform;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.Student;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.UserRepository;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.User;

public class LoginPlatformTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginPlatform loginPlatform;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateSuccessful(){
        User user = new Student();
        user.setUserName("userTest");
        user.setUserPassword("passwordTest");

        when(userRepository.findByUserName("userTest")).thenReturn(user);

        boolean result = loginPlatform.authenticate("userTest", "passwordTest");
        assertTrue(result);

        System.out.println("testAuthenticateSuccessful passed");
    }

    @Test
    public void testAuthenticateFailedUserName(){
        when(userRepository.findByUserName("invalidUser")).thenReturn(null);
        boolean result = loginPlatform.authenticate("invalidUser", "invalidPassword");
        assertFalse(result);

        System.out.println("testAuthenticateFailedUserName passed");
    }

    @Test
    public void testAuthenticateFailedUserPassword(){
        User user = new Student();
        user.setUserName("userTest");
        user.setUserPassword("password");

        when(userRepository.findByUserName("userTest")).thenReturn(user);
        boolean result = loginPlatform.authenticate("userTest", "invalidPassword");
        assertFalse(result);

        System.out.println("testAuthenticateFailedUserPassword passed");
    }




}
