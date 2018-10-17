package com.codemantra.maestro.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamSource;
import com.codemantra.maestro.model.MailConfig;


@Service
public class MailService {

	/*
	 * The Spring Framework provides an easy abstraction for sending email by using
	 * the JavaMailSender interface, and Spring Boot provides auto-configuration for
	 * it as well as a starter module.
	 */
	private JavaMailSender javaMailSender;

	/**
	 * 
	 * @param javaMailSender
	 */
	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * 
	 * @param user
	 * @throws MailException
	 */

	public boolean sendEmail(String subject, String content, MailConfig emailIds) throws MailException {

		/*
		 * This JavaMailSender Interface is used to send Mail in Spring Boot. This
		 * JavaMailSender extends the MailSender Interface which contains send()
		 * function. SimpleMailMessage Object is required because send() function uses
		 * object of SimpleMailMessage as a Parameter
		 */

		System.out.println("Email id's " + emailIds.getUserEmailIds());
		String emailId = emailIds.getUserEmailIds();
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(emailId);
		//mail.setTo(emailId);
		mail.setFrom("maestroqs@codemantra.in");
		mail.setSubject(subject);
		mail.setText(content);

		/*
		 * This send() contains an Object of SimpleMailMessage as an Parameter
		 */
		javaMailSender.send(mail);
		return true;
	}

	/**
	 * This fucntion is used to send mail that contains a attachment.
	 * 
	 * @param user
	 * @throws MailException
	 * @throws MessagingException
	 */
	 public boolean sendEmailWithAttachment(String subject, String content, MailConfig emailIds) throws MailException, MessagingException {

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom("maestroqs@codemantra.in");
		//helper.setTo(emailIds.getUserEmailIds());
		System.out.println("===" +emailIds.getUserEmailIds());
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailIds.getUserEmailIds() ));
		helper.setSubject(subject);
		helper.setText(content);

		//FileSystemResource file = new FileSystemResource("D:/Dhivakar/ACS-Editor Requirements.xlsx");
		//helper.addAttachment(file.getFilename(), file);

		javaMailSender.send(message);
		return true;
	}
	 
	 public boolean sendEmailWithAttachmentAndContent(String subject, String content, MailConfig emailIds, String fileName, MultipartFile file) throws MailException, MessagingException {

			MimeMessage message = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			/*helper.setFrom("maestroqs@codemantra.in");
			//helper.setTo(emailIds.getUserEmailIds());
			//System.out.println("===" +emailIds.getUserEmailIds());
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dhivakart@codemantra.co.in"));
			helper.setSubject("Testing Mail API with Attachment");
			helper.setText("Please find the attached document below.");*/

			 javaMailSender.send(new MimeMessagePreparator() {
				 
		            @Override
		            public void prepare(MimeMessage mimeMessage) throws Exception {
		                MimeMessageHelper messageHelper = new MimeMessageHelper(
		                        mimeMessage, true, "UTF-8");
		                messageHelper.setFrom("maestroqs@codemantra.in");
		                //messageHelper.setTo("dhivakart@codemantra.co.in");
		                messageHelper.setTo(InternetAddress.parse(emailIds.getUserEmailIds()));
		               // message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailIds.getUserEmailIds() ));
		                messageHelper.setSubject(subject);
		                messageHelper.setText(content);
		                 
		                // determines if there is an upload file, attach it to the e-mail
		                String attachName = fileName;
		                if (!attachName.equals("")) {
		 
		                    messageHelper.addAttachment(attachName, new InputStreamSource() {
		                         
		                        @Override
		                        public InputStream getInputStream() throws IOException {
		                            return file.getInputStream();
		                        }
		                    });
		                }
		                 
		            }
		 
		        });

			//javaMailSender.send(message);
			return true;
		}
}
