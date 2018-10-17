package com.codemantra.maestro.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.codemantra.maestro.model.MailConfig;
import com.codemantra.maestro.response.MailRequestWrapper;
import com.codemantra.maestro.response.MailResponse;
import com.codemantra.maestro.service.MailConfigService;
import com.codemantra.maestro.service.MailService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@Controller
public class EmailController {

	private static final Logger log = LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	MailConfigService mailConfigService;
	
	@Autowired
	MailService mailService;
	
	
	 /*@PostMapping("/testUpload")
     public ResponseEntity<MailResponse> upload(@RequestParam("file") MultipartFile file, 
                          @RequestParam("email") MailRequestWrapper request) {
		 MailResponse response = new MailResponse();
		 
		 System.out.println("===Group name===" +request.getGroupName());
		 System.out.println("===Subject===" +request.getSubject());
		 System.out.println("===Content===" +request.getMessage());
		 
		 response.setMessage("Mail sent successfully.");
		 response.setStatus(HttpStatus.OK);
		 return new ResponseEntity<MailResponse>(response, HttpStatus.OK);
         //return user + "\n" + file.getOriginalFilename() + "\n" + file.getSize();
     }*/
	
	/**
	 * 
	 * @param mailRequest
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public ResponseEntity<MailResponse> sendMail(@RequestParam("file") MultipartFile file, 
            @RequestParam("email") MailRequestWrapper request) throws IOException{
		log.info("Sending email {} " +request.getGroupName());
		MailResponse response = new MailResponse();
	
		try {
			MailConfig mailConfig = mailConfigService.getMailConfig(request.getGroupName());
			if(mailConfig != null){
				
				boolean flag = mailService.sendEmailWithAttachmentAndContent(request.getSubject(),request.getMessage(), mailConfig, file.getOriginalFilename(), file);
				if(flag){
					response.setMessage("Mail sent to {} " + mailConfig.getGroupName());
					response.setStatus(HttpStatus.FOUND);
				}
			}else{
				response.setMessage("Group name not exists.");
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Error while sending the email {} " +e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<MailResponse>(response, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param mailRequest
	 * @return
	 * @throws IOException 
	 */
	/*@RequestMapping(value = "/sendMailWithAttachment", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<MailResponse> sendMailWithAttachment(@RequestParam("file") MultipartFile file) throws IOException{
		//log.info("Sending email {} " +mailRequest.getGroupName());
		MailResponse response = new MailResponse();
	
		try {
			System.out.println("File name :: " +file.getName());
			//MailConfig mailConfig = mailConfigService.getMailConfig(mailRequest.getGroupName());
			if(mailConfig != null){
				
				//boolean flag = mailService.sendEmailWithAttachment(file.getOriginalFilename(), file);
				if(flag){
					response.setMessage("Mail sent to {} " + mailConfig.getGroupName());
					response.setStatus(HttpStatus.FOUND);
				}
			}else{
				response.setMessage("Group name not exists.");
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Error while sending the email {} " +e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<MailResponse>(response, HttpStatus.OK);
	}*/
	
	@Component
    public static class StringToMailRequestConverter implements Converter<String, MailRequestWrapper> {

        @Autowired
        private ObjectMapper objectMapper;

        @Override
        @SneakyThrows
        public MailRequestWrapper convert(String source) {
            try {
				return objectMapper.readValue(source, MailRequestWrapper.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
        }
    }
}
