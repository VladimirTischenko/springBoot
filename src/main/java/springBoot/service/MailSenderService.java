package springBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by Vladimir on 27.05.2020.
 */
@Service
public class MailSenderService {
    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender sender;

    @Autowired
    public MailSenderService(@Qualifier("getMailSender") JavaMailSender sender) {
        this.sender = sender;
    }

    void send(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        sender.send(message);
    }
}
