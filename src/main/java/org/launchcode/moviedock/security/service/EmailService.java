package org.launchcode.moviedock.security.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private String subject;
    private String recipient;
    private String msgBody;
//    private URL confirmationLink;

    public void sendEmail(String subject, String recipient, String msgBody) {

        mailSender.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException {

//                String html = msgBody + "<a href='http://localhost:8080/#'>Click Here</a>";

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom("Moviedock <no-reply@moviedock.com>");
                message.setSubject(subject);
                message.setTo(recipient);
                message.setText(msgBody);
//                message.setText("link", html);
//                message.addInline("myLogo", new ClassPathResource("img/mylogo.gif"));
//                message.addAttachment("myDocument.pdf", new ClassPathResource("doc/myDocument.pdf"));
            }
        });
    }
}
