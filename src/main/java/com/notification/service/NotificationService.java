package com.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendEmail(MimeMessage mimeMessage) {
		try {
			javaMailSender.send(mimeMessage);
			return true;
		} catch (MailException e) {
			// Handle the exception
			return false;
		}
	}

	public boolean sendEmailFromDetails(Map<String, Object> messageDetails) {
		String from = (String) messageDetails.get("sender");
		List<String> toList = (List<String>) messageDetails.get("receivers");
		String subject = (String) messageDetails.get("object");
		String text = (String) messageDetails.get("message");
		List<Map<String, String>> files = (List<Map<String, String>>) messageDetails.get("files");

		if (from == null || toList == null || subject == null || text == null) {
			// Handle the null values, e.g., log an error message or throw an exception
			return false;
		}

		String[] to = toList.toArray(new String[0]);

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);

			if (files != null) {
				for (Map<String, String> file : files) {
					String fileName = file.get("name");
					String fileValue = file.get("value");
					byte[] fileBytes = Base64.getDecoder().decode(fileValue);
					helper.addAttachment(fileName, new ByteArrayResource(fileBytes));
				}
			}

			return sendEmail(mimeMessage);
		} catch (MessagingException e) {
			// Handle the exception
			return false;
		}
	}
}