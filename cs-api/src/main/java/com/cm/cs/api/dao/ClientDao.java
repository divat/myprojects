package com.cm.cs.api.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cm.cs.api.config.hibernate.Dao;
import com.cm.cs.api.model.Client;
import com.cm.cs.api.model.ClientList;
import com.cm.cs.api.model.Division;

/**
 * 
 * @author DHIVAKART
 *
 */
@Repository
@Transactional
public class ClientDao extends Dao<Client>{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public ClientDao(){
		super(Client.class);
	}
	
	/**
	 * Find the client
	 * @param clientName
	 * @return
	 */
	public Client findByClientValue(String clientName){
		Client client = (Client) getSession().createQuery("from Client where lower(clientName) = lower(:clientName)")
				.setParameter("clientName", clientName)
				.uniqueResult();
		return client;
	}
	
	public Client findClientByDivision(String clientName, Division division){
		Client client = (Client) getSession().createQuery("from Client c ")
				.setParameter("clientName", clientName)
				.uniqueResult();
		return client;
	}
	
	public List<ClientList> clientList(String clientName){
		String query = "select client_id,client_name from mast_cs_client where lower(client_name) like :data";
		SQLQuery queryList = (SQLQuery) getSession().createSQLQuery(query).setString("data", clientName + "%");
		List<Object[]> data = queryList.list();
		
		List<ClientList> clientList = new ArrayList<ClientList>();
		for(Object[] row : data){
			ClientList clientDetails = new ClientList();
			clientDetails.setClientId(row[0].toString());
			clientDetails.setClientName(row[1].toString());
			clientList.add(clientDetails);
		}
		return clientList;
	}
}
