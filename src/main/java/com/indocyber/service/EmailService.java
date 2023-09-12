package com.indocyber.service;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {
    void sendEmail(String to, String subject, String body) throws MessagingException, IOException;
}
