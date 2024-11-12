package org.sysc4806g30.graduateadmissionsmanagementsystem.system;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailingSystem {
    public static void main(String[] args) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("ruangfafa.mao@gmail.com");
        mailSender.setPassword("ezhf felu uyge ecyt");


        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // 可选，用于调试


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ruangfafa.mao@gmail.com"); // 设置发件人邮箱地址
        message.setTo("clksdysjsh0317@gmail.com");
        message.setSubject("Notification");
        message.setText("Test Text");


        mailSender.send(message);
        System.out.println("Email sent successfully.");
    }
}
