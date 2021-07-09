package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmailWithoutCc() {
        //given
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .toCC(null)
                .subject("Test")
                .message("Test message")
                .build();

        //when
        simpleEmailService.send(mail);

        //then
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }

    @Test
    public void shouldSendEmailWithCc() {
        //given
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .toCC("cc@test.com")
                .subject("Test")
                .message("Test message")
                .build();

        //when
        simpleEmailService.send(mail);

        //then
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }
}