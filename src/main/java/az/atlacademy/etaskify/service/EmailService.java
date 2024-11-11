package az.atlacademy.etaskify.service;

import az.atlacademy.etaskify.service.impl.EmailServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailService implements EmailServiceImpl {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendTo(String email, Object subject) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText(subject.toString());
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
