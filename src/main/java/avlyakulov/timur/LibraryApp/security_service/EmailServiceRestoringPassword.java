package avlyakulov.timur.LibraryApp.security_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceRestoringPassword {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceRestoringPassword(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public int sendEmail(String sendToEmail) {
        Random random = new Random();
        int min = 100000; // Минимальное шестизначное число (100000)
        int max = 999999; // Максимальное шестизначное число (999999)
        int randomNumber = random.nextInt(max - min + 1) + min;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendToEmail);
        message.setSubject("Відновлення паролю");
        message.setText("Ваш таємний код для відновлення паролю " + randomNumber);
        javaMailSender.send(message);
        return randomNumber;
    }
}