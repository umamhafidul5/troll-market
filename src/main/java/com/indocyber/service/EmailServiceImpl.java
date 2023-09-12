package com.indocyber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, ResourceLoader resourceLoader) {
        this.mailSender = mailSender;
        this.resourceLoader = resourceLoader;
    }
    @Override
    @Async
    public void sendEmail(String to, String subject, String body) throws MessagingException, IOException {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(to);
//        mailMessage.setSubject(subject);
//        mailMessage.setText(bodyEmail);

        body = body.replace("[", "").replace("]", "");

        String[] listItem = body.split(",");

        String collect = Arrays.stream(listItem).map(s -> {
            String[] tdArray = s.split("\\|");
            String td = Arrays.stream(tdArray).map(s1 -> {
                s1 = "<td>" + s1 + "</td>";
                return s1;
            }).collect(Collectors.joining());
            return "<tr>" + td + "</tr>";
        }).collect(Collectors.joining());

        Resource resource = resourceLoader.getResource("classpath:emails/body-email.html");
        InputStreamReader reader = new InputStreamReader(resource.getInputStream());

        String bodyEmail = FileCopyUtils.copyToString(reader);

        bodyEmail = bodyEmail.replace("<tr>%DATA%</tr>", collect);

        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(bodyEmail, true);

        mailSender.send(helper.getMimeMessage());
    }
}
