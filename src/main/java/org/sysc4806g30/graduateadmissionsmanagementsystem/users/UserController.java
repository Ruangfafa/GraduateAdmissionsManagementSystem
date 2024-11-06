package org.sysc4806g30.graduateadmissionsmanagementsystem.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @ResponseBody
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

}