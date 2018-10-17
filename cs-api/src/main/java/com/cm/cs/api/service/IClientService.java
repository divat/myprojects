package com.cm.cs.api.service;

import java.util.List;

import com.cm.cs.api.model.Client;
import com.cm.cs.api.model.ClientList;

public interface IClientService {

	List<ClientList> loadClients(String clientName);
	
	List<Client> clientList();
	
	Client findByClientName(String clientName);
}
