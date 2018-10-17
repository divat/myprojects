package com.cm.style.profile.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cm.style.profile.model.Client;
import com.cm.style.profile.model.Publication;
import com.cm.style.profile.model.Publisher;
import com.cm.style.profile.model.Styles;
import com.cm.style.profile.request.wrapper.StyleWrapper;
import com.cm.style.profile.response.StyleProfileResponse;
import com.cm.style.profile.service.IClientService;
import com.cm.style.profile.service.IPublicationService;
import com.cm.style.profile.service.IPublisherService;
import com.cm.style.profile.service.IStyleService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Style profile controller
 * @author DHIVAKART
 *
 */
@RestController
public class StyleProfileController {

	private static final Logger log = LoggerFactory.getLogger(StyleProfileController.class);
	
	@Autowired
	private IPublisherService publisherService;
	
	@Autowired
	private IPublicationService publicationService;
	
	@Autowired
	private IClientService clientService;
	
	@Autowired
	private IStyleService styleService;
	
	/**
	 * Add the new style
	 * @param styles
	 * @param publisherName
	 * @param publicationName
	 * @param clientName
	 * @return
	 */
	@PostMapping(value = "/addStyles")
	public ResponseEntity<StyleProfileResponse> addStyle(@RequestBody(required=true) String styles, @RequestParam(value="publisherName", required=true) String publisherName, @RequestParam(value="publicationName", required=true) String publicationName, @RequestParam(value="client", required=true) String clientName){
		log.info("Adding style profile data for " +publisherName+"-"+publicationName+"-"+clientName);

		StyleProfileResponse response = new StyleProfileResponse(); 
			Publisher publisher = null;
			Publication publication = null;
			Styles styleName = null;
			Client client = null;
		try{
			boolean notNullCheck = checkNonNull(clientName, publisherName, publicationName, styles);
			if(notNullCheck){
				client = clientService.findByClient(clientName);

				if(client == null){
							response.setMessage("Client not found");
							response.setStatus(HttpStatus.NOT_FOUND);
							return new ResponseEntity<StyleProfileResponse>(response, HttpStatus.OK);
				}else{
					publisher = publisherService.findByPublisherName(publisherName);
					if(publisher != null){
						publication = publicationService.findByPublicationName(publicationName, publisher);
						if(publication != null){
							styleName = styleService.findStylesByPublication(publication.getPublicationName(), publisher, client);
							if(styleName != null){
								//Update style
								
								if(styleName.getStyles().equals(styles)){
									log.info("Style already exists.");
									response.setMessage("Style already exists.");
									response.setStatus(HttpStatus.FOUND);
								}else{
									log.info("Update the style & move the existing style to log.");
									int updateStyle = styleService.update(publication, styles, client, styleName.getCreatedOn());
									if(updateStyle > 0){
										//move the existing styles associated with publication to styles_history
										log.info("Moved the existing style to log.");
										response.setMessage("Style updated.");
										response.setStatus(HttpStatus.CREATED);	
									}else{
										response.setMessage("Style not updated.");
										response.setStatus(HttpStatus.CREATED);
									}
								}
							}else{
								//add new style
								if(styles != null){
									Styles styleObj = styleService.persistStyles(styles, publication, client);
									if(styleObj != null){
										response.setMessage("New style created for publication :: " +publication.getPublicationName());
										response.setStatus(HttpStatus.CREATED);
									}else{
										response.setMessage("Style not created for publication :: " +publication.getPublicationName());
										response.setStatus(HttpStatus.NOT_FOUND);
									}
								}else{
									response.setMessage("Style should not be null.");
									response.setStatus(HttpStatus.NOT_FOUND);
								}
							}
						}else{
							response.setMessage("Publication not found");
							response.setStatus(HttpStatus.NOT_FOUND);
						}
					}else{
						response.setMessage("Publisher not found");
						response.setStatus(HttpStatus.NOT_FOUND);
					}
				}
			}else{
				response.setMessage("Required values should not be empty.");
				response.setStatus(HttpStatus.OK);
			}
		}catch (Exception e) {
				log.error("Error while adding the style profile data :: " +e.getMessage());
				response.setMessage("Error while adding the style details :: " +e.getMessage());
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<StyleProfileResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Store style profile data
	 * @param styles
	 * @param publisher
	 * @param publicaion
	 * @param clientName
	 * @param uID
	 * @return
	 */
	@PostMapping(value = "/addStyle")
	public ResponseEntity<StyleProfileResponse> postStyleProfile(@RequestBody(required=true) String styles, @RequestParam(value="publisherName", required=true) String publisherName,
			 @RequestParam(value="publicationName", required=true) String publicationName, @RequestParam(value="client", required=true) String clientName,
			 @RequestParam(value="uID", required=false) String uID){
		log.info("Adding style profile data for " +publisherName+"-"+publicationName+"-"+clientName);
		
		StyleProfileResponse response = new StyleProfileResponse(); 
		Publisher publisher = null;
		Publication publication = null;
		Styles styless = null;
		Client client = null;
		try {
			boolean notNullCheck = checkNonNull(clientName, publisherName, publicationName, styles);
			if(notNullCheck){
				client = clientService.findByClientName(clientName);
				publisher = publisherService.checkPublisherExists(publisherName);
				
				if(publisher != null){
					publication = publicationService.findByPublication(publicationName, publisher, styles, client);
					
					if(publication != null){
						List<Styles> style = publication.getStyle();
						if(style.size() > 0){
							publication.setStyle(style);
							
							for(Styles s : style){
								
								if(s.getStyles() == null){
									System.out.println("Style null so capture new style...");
								}else{
								
									if(s.getStyles().equals(styles)){
										log.info("Style already exists.");
										response.setMessage("Style already exists.");
										response.setStatus(HttpStatus.FOUND);
									}else{
										log.info("Update the style & move the existing style to log.");
										int updateStyle = styleService.update(publication, styles, client, s.getCreatedOn());
										if(updateStyle > 0){
											//move the existing styles associated with publication to styles_history
											log.info("Moved the existing style to log.");
											response.setMessage("Style updated.");
											response.setStatus(HttpStatus.CREATED);	
										}else{
											response.setMessage("Style not updated.");
											response.setStatus(HttpStatus.CREATED);
										}
									}
								}
							}
						}else{
							response.setMessage("Style not found to update.");
							response.setStatus(HttpStatus.NOT_FOUND);
						}
						
					}else{
						styless = styleService.findStylesByPublication(publicationName, publisher, client);
						
						if(styless != null){						
							response.setPublisherName(publisherName);
							response.setPublicationName(publicationName);
							response.setClient(clientName);
							/*response.setuID(uID);*/
							response.setMessage("New style added for " +publisherName+"-"+publicationName);
							response.setStatus(HttpStatus.CREATED);
						}else{
							response.setPublisherName(publisherName);
							response.setPublicationName(publicationName);
							response.setClient(clientName);
							/*response.setuID(uID);*/
							response.setMessage("Error while adding the style.");
							response.setStatus(HttpStatus.NOT_FOUND);
						}
					}
				}else{
					response.setMessage("Publisher not found.");
					response.setStatus(HttpStatus.NOT_FOUND);
				}
			}else{
				response.setMessage("Required style parameter's missing.");
				response.setStatus(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error("Error while getting the style profile data :: " +e.getMessage());
			response.setMessage("Error while getting the style details :: " +e.getMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<StyleProfileResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Fetch the style details
	 * @param plName
	 * @param pnName
	 * @param clientName
	 * @param uID
	 * @return
	 */
	@GetMapping(value = "/getStyles")
	public ResponseEntity<StyleProfileResponse> getStyleProfile(@RequestParam(value="publisherName", required=true) String plName, @RequestParam(value="publicationName", required=true) String pnName,
			@RequestParam(value="client", required=true) String clientName, @RequestParam(value="uID", required=false) String uID){
		log.info("Getting the style profile details for " +plName+"-"+pnName+"-"+clientName);
		
		StyleProfileResponse response = new StyleProfileResponse();
		Publisher publisher = null;
		Publication publication = null;
		Styles style = null;
		Client client = null;
		try {
			if(clientName != null && plName != null && pnName != null
					&& clientName != "" && plName != "" && pnName != ""){
					client = clientService.findByClient(clientName);
					if(client == null){
						response.setMessage("Client not found");
						response.setStatus(HttpStatus.NOT_FOUND);
						return new ResponseEntity<StyleProfileResponse>(response, HttpStatus.OK);
					}else{
						publisher = publisherService.findByPublisherName(plName);
						if(publisher != null){
							publication = publicationService.findByPublicationName(pnName, publisher);
						if(publication != null){
							style = styleService.findStylesByPublication(publication.getPublicationName(), publisher, client);
							if(style != null){
								/*response.setPublisherName(publisher.getPublisherName());
								response.setPublicationName(pnName);
								response.setClient(client.getClientName());*/
								response.setStyle(style.getStyles());
								/*response.setStatus(HttpStatus.FOUND);*/
							}else{
								response.setMessage("Style not found");
								response.setStatus(HttpStatus.NOT_FOUND);
							}
						}else{
							response.setMessage("Publication not found");
							response.setStatus(HttpStatus.NOT_FOUND);
						}
					}else{
						response.setMessage("Publisher not found");
						response.setStatus(HttpStatus.NOT_FOUND);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error while fetching the style details for " +plName+"-"+pnName+"-"+uID+"-"+clientName);
			log.error(e.getMessage());
			response.setMessage("Error while fetching the style details.");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<StyleProfileResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Update the style profile execution
	 * @param plName
	 * @param pnName
	 * @param clientName
	 * @param isStyleUpdated
	 * @return
	 */
	@PostMapping(value = "/updateStyleExecution")
	public ResponseEntity<StyleProfileResponse> updateStyleValidation(@RequestParam(value="publisherName", required=true) String plName,
			@RequestParam(value="publicationName", required=true) String pnName,
			@RequestParam(value="client", required=true) String clientName, 
			@RequestParam(value = "isStyleUpdated", required = true) String isStyleUpdated){
		log.info("Updating the style execution value :: " +isStyleUpdated);
		
		StyleProfileResponse response = new StyleProfileResponse();
		Publisher publisher = null;
		Publication publication = null;
		Styles style = null;
		Client client = null;
		try {
			if (clientName != null && plName != null && pnName != null && clientName != "" && plName != ""
					&& pnName != "") {
				client = clientService.findByClient(clientName);
				if (client == null) {
					response.setMessage("Client not found");
					response.setStatus(HttpStatus.NOT_FOUND);
					return new ResponseEntity<StyleProfileResponse>(response, HttpStatus.OK);
				} else {
					publisher = publisherService.findByPublisherName(plName);
					if (publisher != null) {
						publication = publicationService.findByPublicationName(pnName, publisher);
						if (publication != null) {
							style = styleService.findStylesByPublication(publication.getPublicationName(), publisher,
									client);
							if (style != null) {
									style = styleService.updateStyleExecution(style, isStyleUpdated);
									if(style != null){
										response.setMessage("Style profile execution success.");
										response.setIsStyleExecuted(String.valueOf(style.isStyleProfileExecuted()));
									}else{
										response.setMessage("Style profile execution failed.");
										response.setIsStyleExecuted(String.valueOf(true));
									}
							} else {
								response.setMessage("Style not found");
								response.setStatus(HttpStatus.NOT_FOUND);
							}
						} else {
							response.setMessage("Publication not found");
							response.setStatus(HttpStatus.NOT_FOUND);
						}
					} else {
						response.setMessage("Publisher not found");
						response.setStatus(HttpStatus.NOT_FOUND);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<StyleProfileResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * Get the style execution status
	 * @param plName
	 * @param pnName
	 * @param clientName
	 * @return
	 */
	@GetMapping(value = "/getStyleExecutionStatus")
	public ResponseEntity<StyleProfileResponse> getStyleExecutionStatus(@RequestParam(value = "publisherName") String plName,
			@RequestParam(value = "publicationName") String pnName, @RequestParam(value = "client") String clientName){
		log.info("Getting the style execution status {} " + plName +"-"+pnName+"-"+clientName);
		StyleProfileResponse response = new StyleProfileResponse();
		Publisher publisher = null;
		Publication publication = null;
		Styles style = null;
		Client client = null;
		try {
			if (clientName != null && plName != null && pnName != null && clientName != "" && plName != ""
					&& pnName != "") {
				client = clientService.findByClient(clientName);
				if (client == null) {
					response.setMessage("Client not found");
					response.setStatus(HttpStatus.NOT_FOUND);
					return new ResponseEntity<StyleProfileResponse>(response, HttpStatus.OK);
				}else{
					publisher = publisherService.findByPublisherName(plName);
					if (publisher != null) {
						publication = publicationService.findByPublicationName(pnName, publisher);
						if (publication != null) {
							style = styleService.findStylesByPublication(publication.getPublicationName(), publisher, client);
							if(style != null){
								response.setMessage("Style profile execution success.");
								response.setIsStyleExecuted(String.valueOf(style.isStyleProfileExecuted()));
							}else{
								response.setMessage("Style not found.");
								response.setIsStyleExecuted(String.valueOf(true));
							}
						}else{
							response.setMessage("Publication not found");
							response.setStatus(HttpStatus.NOT_FOUND);
						}
					}else{
						response.setMessage("Publisher not found");
						response.setStatus(HttpStatus.NOT_FOUND);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error while getting the style execution status {} " + plName +"-"+pnName+"-"+clientName +"." +e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<StyleProfileResponse>(response, HttpStatus.OK);
	}
	
	
	/**
	 * Check whether request parameters are not null
	 * @param clName
	 * @param plName
	 * @param pnName
	 * @param styles
	 * @param uID
	 * @return
	 */
	private boolean checkNonNull(String clName, String plName, String pnName, String styles, String uID){
		if(clName != null && plName != null && pnName != null && styles != null && uID != null 
				&& clName != "''" && plName != "''" && pnName != "''" && styles != "''" && uID != "''"
				&& clName != "" && plName != "" && pnName != "" && styles != "" && uID != ""){
			return true;
		}
		return false;
	}
	
	private boolean checkNonNull(String clName, String plName, String pnName, String styles){
		if(clName != null && plName != null && pnName != null && styles != null 
				&& clName != "''" && plName != "''" && pnName != "''" && styles != "''"
				&& clName != "" && plName != "" && pnName != "" && styles != "" ){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public static List<StyleWrapper> readField(String json) throws IOException {
		List<StyleWrapper> details = new ArrayList<StyleWrapper>();
		StyleWrapper style = new StyleWrapper();
		try {
			if (json != null) {
				ObjectNode object = new ObjectMapper().readValue(json, ObjectNode.class);
				JsonNode publisherNameNode = object.get("publisherName");
				JsonNode publicationNameNode = object.get("publicationName");
				JsonNode uIdNode = object.get("uID");
				JsonNode profileData = object.get("profileData");

				style.setPublisherName(publisherNameNode.textValue());
				style.setPublicationName(publicationNameNode.textValue());
				style.setuID(uIdNode.textValue());
				style.setProfileData(profileData.textValue());
				details.add(style);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return details;
	}
	
	
	public void parse(String json) throws JsonProcessingException, IOException  {
	       JsonFactory factory = new JsonFactory();

	       ObjectMapper mapper = new ObjectMapper(factory);
	       JsonNode rootNode = mapper.readTree(json);  

	       Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
	       while (fieldsIterator.hasNext()) {
	           Map.Entry<String,JsonNode> field = fieldsIterator.next();
	           System.out.println("Key: " + field.getKey().equals("publisherName") + "\tValue:" + field.getValue()); 
	       }
	}
}
