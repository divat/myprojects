package com.cm.cs.api.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cm.cs.api.config.hibernate.Dao;
import com.cm.cs.api.model.AutomationDetails;
import com.cm.cs.api.model.AutomationTools;
import com.cm.cs.api.model.Division;

/**
 * 
 * @author DHIVAKART
 *
 */
@Repository
@Transactional
public class AutomationToolsDao extends Dao<AutomationTools>{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public AutomationToolsDao(){
		super(AutomationTools.class);
	}
	
	/**
	 * Fetch the automation tool details
	 * @param projectName
	 * @param clientName
	 * @param automationName
	 * @return
	 */
	public AutomationTools findByAutomationDetails(String projectName, String clientName, String automationName){
		AutomationTools automationTools = (AutomationTools) getSession().createQuery("from AutomationTools where projectName = :projectName and clientName = :client and automationName=:automationName")
							.setParameter("projectName", projectName)
							.setParameter("client", clientName)
							.setParameter("automationName", automationName)
							.uniqueResult();
		return automationTools;
	}
	
	/**
	 * Find the automation inputs
	 * @param automationName
	 * @param divisionObj
	 * @param manualMetrics
	 * @param manualPages
	 * @param automationMetrics
	 * @param automationPages
	 * @return
	 */
	public AutomationTools findByAutomationInputs(String automationName, Division divisionObj/*, String manualMetrics, String manualPages, String automationMetrics, String automationPages*/){
		AutomationTools automationTools = (AutomationTools) getSession().createQuery("from AutomationTools a where a.automationName=:automationName and a.division.id=:divisionId")
					.setParameter("automationName", automationName)
					/*.setParameter("manualPages", manualPages)
					.setParameter("manualMetrics", manualMetrics)
					.setParameter("automationPages", automationPages)
					.setParameter("automationMetrics", automationMetrics)*/
					.setParameter("divisionId", divisionObj.getId())
					.uniqueResult();
		return automationTools;
	}
	
	public List<AutomationTools> automationDetailsList(){
		List<AutomationTools> automationList = getSession().createQuery("from AutomationTools").list();
		return automationList;
	}
}
