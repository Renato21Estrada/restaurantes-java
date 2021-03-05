package com.boot.bookingrestaurantapi.services.impl;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.dtos.EmailTemplateDto;
import com.boot.bookingrestaurantapi.entities.Notification;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.exceptions.NotFoundException;
import com.boot.bookingrestaurantapi.repositories.NotificationRepository;
import com.boot.bookingrestaurantapi.services.EmailService;


@Service
public class EmailServiceImpl implements EmailService{

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Override
	public String ProcessSendEmail(String receiver, String templateCode, String currentName)
			throws BookingException {
		
		final EmailTemplateDto emailTemplateDto = findTemplateAndRepleace(templateCode, currentName);
		
		this.sendEmail(receiver, emailTemplateDto.getSubject(), emailTemplateDto.getTemplate());
		
		return "EMAIL_SEND";
	}

	private void sendEmail(final String receiver , final String subject , final String template) throws BookingException{
		
		final MimeMessage email= javaMailSender.createMimeMessage();
		final MimeMessageHelper content;
		
		try {
			content = new MimeMessageHelper(email, true);
			content.setSubject(subject);
			content.setTo(receiver);
			content.setText(template, true);
		} catch (Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		javaMailSender.send(email);
	}
	
	private EmailTemplateDto findTemplateAndRepleace(final String templateCode , final String currentName) throws BookingException{
		
		final Notification notificationTemplate = notificationRepository.findByTemplateCode(templateCode)
				.orElseThrow(() -> new NotFoundException("EMAIL_NOT_FOUND","EMAIL_NOT_FOUND"));
		
		final EmailTemplateDto emailTemplateDto = new EmailTemplateDto();
		emailTemplateDto.setSubject(notificationTemplate.getTemplateCode());
		emailTemplateDto.setTemplate(notificationTemplate.getTemplate().replaceAll("\\{"+"name"+"\\}", currentName));
		return emailTemplateDto;
		
	}
	
	
	
}
