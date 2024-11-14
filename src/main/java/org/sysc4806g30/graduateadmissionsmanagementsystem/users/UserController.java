package org.sysc4806g30.graduateadmissionsmanagementsystem.users;

import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    @ResponseBody
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/student/{uid}")
    public String showStudentPage(@PathVariable Long uid) {
        Optional<User> userOptional = userRepository.findById(uid);
        Student student = (Student) userOptional.get();
        return "student";
    }

    @GetMapping("/professor/{uid}")
    public String showProfessorPage(@PathVariable Long uid) {
        Optional<User> userOptional = userRepository.findById(uid);
        Professor professor = (Professor) userOptional.get();
        return "professor";
    }

    @GetMapping("/admin/{uid}")
    public String showAdminPage(@PathVariable Long uid) {
        Optional<User> userOptional = userRepository.findById(uid);
        Admin admin = (Admin) userOptional.get();
        return "admin";
    }
}
