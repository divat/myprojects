package com.cm.style.profile.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cm.style.profile.dao.ClientDao;
import com.cm.style.profile.model.Client;
import com.cm.style.profile.service.IClientService;

@Service 
@Qualifier("clientService")
public class ClientServiceImpl implements IClientService{

	private static final Date utilDate = new Date();
	private static final Timestamp date = new Timestamp(utilDate.getTime());
	
	@Autowired
	ClientDao clientDao;
	
	@Override
	public Client findByClientName(String clientName) {
		Client client = clientDao.findByStringValue("clientName", clientName);
		if(client != null){
			return client;
		}else{
			Client c = new Client();
			c.setClientName(clientName);
			c.setActive(true);
			c.setDeleted(false);
			c.setCreatedOn(date);
			c = clientDao.save(c);
			return c;
		}
	}
	
	/**
	 * Get the client details to store
	 * @param clientName
	 * @return
	 */
	public Client captureClientDetails(String clientName){
		Client client = new Client();
		try {
			if(clientName != null){
				client.setClientName(clientName);
				client.setActive(true);
				client.setDeleted(false);
				client.setCreatedOn(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return client;
	}

	@Override
	public Client findByClient(String clientName) {
		Client client = clientDao.findByStringValue("clientName", clientName);
		return client;
	}

	@Override
	public List<Client> loadClients(String clientName) {
		List<Client> clientList = clientDao.findByString("clientName", clientName);
		return clientList;
	}

}
