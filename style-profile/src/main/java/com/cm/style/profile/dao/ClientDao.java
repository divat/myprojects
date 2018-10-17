package com.cm.style.profile.dao;

import org.springframework.stereotype.Repository;

import com.cm.style.profile.config.hibernate.Dao;
import com.cm.style.profile.model.Client;

@Repository
public class ClientDao extends Dao<Client>{

	public ClientDao(){
		super(Client.class);
	}
}
