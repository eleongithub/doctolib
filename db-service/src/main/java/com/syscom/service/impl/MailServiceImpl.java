package com.syscom.service.impl;

import com.syscom.domains.dto.MailDTO;
import com.syscom.service.MailService;
import com.syscom.service.MessageService;
import com.syscom.service.exceptions.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Service métier d'envoi des mails.
 *
 * Created by Eric Legba on 15/08/17.
 */
@Service
@PropertySource("classpath:db-service.properties")
@Transactional(rollbackFor = Exception.class)
public class MailServiceImpl implements MailService {

    private static Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MessageService messageService;

    @Value("${push.mail.block.msg}")
    private String blockMsg;

    @Value("${doctolib.mail.send.from}")
    private String mailFrom;

    @Value("${push.mail}")
    private boolean pushMail;

    @Override
    public void sendMessage(MailDTO mailDTO) {
        if(!pushMail) {
            LOGGER.warn(messageService.getMessage("warn.push.mail.disabled"));
            return;
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject(mailDTO.getSubject());
            String from = mailDTO.getFrom() != null ? mailDTO.getFrom() : mailFrom;
            message.setFrom(from);
            message.setTo(mailDTO.getTo());
            Context context = new Context();
            mailDTO.getDatas().forEach((key, value) -> context.setVariable(key, value));
            String htmlContent = templateEngine.process(new ClassPathResource(mailDTO.getTemplate()).getPath(), context);
            message.setText(htmlContent, true);

            if (mailDTO.getAttachments() != null) {
                for (File file : mailDTO.getAttachments()) {
                    message.addAttachment(file.getName(), file);
                }
            }
            javaMailSender.send(mimeMessage);
        } catch (javax.mail.MessagingException exception) {
            throw new TechnicalException(exception);
        }
    }
}
