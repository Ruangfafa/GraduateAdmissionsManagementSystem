package org.sysc4806g30.graduateadmissionsmanagementsystem.loginplatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.User;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.UserRepository;

@Service
public class LoginPlatform {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            return user.getUserPassword().equals(password);
        }
        return false;
    }
}
