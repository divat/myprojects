package com.cm.cs.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.cs.api.dao.ClientDao;
import com.cm.cs.api.model.Client;
import com.cm.cs.api.model.ClientList;
import com.cm.cs.api.service.IClientService;

@Service
public class ClientServiceImpl implements IClientService{

	@Autowired
	ClientDao clientDao;
	
	@Override
	public List<ClientList> loadClients(String clientName) {
		return clientDao.clientList(clientName);
	}

	@Override
	public List<Client> clientList() {
		return clientDao.findAll();
	}

	@Override
	public Client findByClientName(String clientName) {
		return clientDao.findByClientValue(clientName);
	}

}
