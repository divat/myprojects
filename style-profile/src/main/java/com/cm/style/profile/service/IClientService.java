package com.cm.style.profile.service;

import java.util.List;

import com.cm.style.profile.model.Client;

public interface IClientService {

	Client findByClientName(String clientName);
	
	Client findByClient(String clientName);
	
	List<Client> loadClients(String clientName);
}
