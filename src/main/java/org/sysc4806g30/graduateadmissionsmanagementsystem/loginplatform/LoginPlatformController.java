package org.sysc4806g30.graduateadmissionsmanagementsystem.loginplatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginPlatformController {

    @Autowired
    private LoginPlatform loginPlatform;

    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest) {
        if (loginPlatform.authenticate(loginRequest.getUsername(), loginRequest.getPassword())) {
            System.out.println("Login Successful");
            return "Login successful!";
        } else {
            System.out.println("Login Failed");
            return "Invalid username or password!";
        }
    }

}
