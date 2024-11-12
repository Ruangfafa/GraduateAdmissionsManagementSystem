package org.sysc4806g30.graduateadmissionsmanagementsystem.loginplatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginPlatformController {

    @Autowired
    private LoginPlatform loginPlatform;

    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    @PostMapping(consumes = "application/json")
    @ResponseBody
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
