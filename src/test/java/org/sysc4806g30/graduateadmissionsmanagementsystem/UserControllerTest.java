package org.sysc4806g30.graduateadmissionsmanagementsystem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.Student;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.User;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.UserController;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.UserRepository;

import java.util.Arrays;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new Student();
        user.setUserName("test");
        user.setUserPassword("password");
        user.seteMail("test@gmail.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("test"))
                .andExpect(jsonPath("$.userPassword").value("password"))
                .andExpect(jsonPath("$.eMail").value("test@gmail.com"))
                .andDo(print());

        System.out.println("testCreateUser passed");

    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new Student();
        user1.setUserName("test1");
        user1.setUserPassword("password1");
        user1.seteMail("test1@gmail.com");

        User user2 = new Student();
        user2.setUserName("test2");
        user2.setUserPassword("password2");
        user2.seteMail("test2@gmail.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].userName").value("test1"))
            .andExpect(jsonPath("$[1].userName").value("test2"))
            .andDo(print());

        System.out.println("testGetAllUsers passed");

    }


}
