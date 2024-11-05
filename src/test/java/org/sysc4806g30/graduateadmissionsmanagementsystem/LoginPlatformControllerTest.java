package org.sysc4806g30.graduateadmissionsmanagementsystem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sysc4806g30.graduateadmissionsmanagementsystem.loginplatform.LoginPlatform;
import org.sysc4806g30.graduateadmissionsmanagementsystem.loginplatform.LoginPlatformController;
import org.sysc4806g30.graduateadmissionsmanagementsystem.loginplatform.LoginRequest;

@WebMvcTest(controllers = LoginPlatformController.class)
public class LoginPlatformControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginPlatform loginPlatform;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

        System.out.println("testShowLoginPage passed");
    }

    @Test
    public void testLoginSuccess() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("userTest");
        loginRequest.setPassword("passwordTest");

        when(loginPlatform.authenticate("userTest", "passwordTest")).thenReturn(true);

        mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful!"));

        System.out.println("testLoginSuccess passed");
    }

    @Test
    public void testLoginFailure() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("userTest");
        loginRequest.setPassword("passwordTest");

        when(loginPlatform.authenticate("invalidUser", "invalidPassword")).thenReturn(false);

        mockMvc.perform(post("/login")
        .content(mapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid username or password!"));

        System.out.println("testLoginFailure passed");
    }


}
