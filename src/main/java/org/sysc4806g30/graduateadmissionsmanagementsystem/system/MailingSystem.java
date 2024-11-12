package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.sysc4806g30.graduateadmissionsmanagementsystem.users.UserRepository;

@Service
public class MailingSystem {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    public void sendEmail(Long userSendTo, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(getUserEmailById(userSendTo));
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @GetMapping
    private String getUserEmailById(@RequestParam("USERUID") long userUID) {
        return  userRepository.findById(userUID).geteMail();
    }
}
