package no.fremtind.insurance.integrationlayer.mockservices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class MailService {

    private final String FROM = "janny.kinende@gmail.com";

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendMail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.info("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using Spring helper
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(FROM);
            message.setSubject(subject);
            message.setText(content, isHtml);

            mailSender.send(mimeMessage);

            log.info("Sent email to address: {}", to);
        } catch (Exception ex) {
            log.info("Email could not be sent to user '{}': {}", to, ex.getMessage());
        }
    }
}