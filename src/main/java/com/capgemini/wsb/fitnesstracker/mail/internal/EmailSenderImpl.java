package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link EmailSender}.
 */
@Service
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender emailSender;
    private final MailProperties mailProperties;

    /**
     * Sends an email.
     *
     * @param email to send
     */
    @Override
    public void send(EmailDto email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getFrom());
        message.setTo(email.toAddress());
        message.setSubject(email.subject());
        message.setText(email.content());
        emailSender.send(message);
    }
}
