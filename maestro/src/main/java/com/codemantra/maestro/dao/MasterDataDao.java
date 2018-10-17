package com.codemantra.maestro.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.codemantra.maestro.config.hibernate.Dao;
import com.codemantra.maestro.model.ClientDetails;
import com.codemantra.maestro.model.MasterData;

@Repository
@Transactional
public class MasterDataDao extends Dao<MasterData>{

	private static final Date utilDate = new Date();
	private static final Timestamp date = new Timestamp(utilDate.getTime());
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public MasterDataDao(){
		super(MasterData.class);
	}
	
	/**
	 * Method to get the list of jobs
	 * @param clientIdList
	 * @param jobIdList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MasterData> checkDuplicateJobs(List<String> clientIdList, List<String> jobIdList){
		List<MasterData> duplicateJobs = getSession().createQuery("from MasterData where clientId in (:clientId) and jobId in (:jobId) and jobStatus not in (:status)")
											 .setParameterList("clientId", clientIdList)
											 .setParameterList("jobId", jobIdList)
											 .setParameter("status", "FAILED")
											.list();
		return duplicateJobs;
	}
	
	/**
	 * Method to find the job 
	 * by jobId and clientId
	 * @param jobId
	 * @param clientId
	 * @return
	 */
	public MasterData findByJobAndClient(String jobId, String clientId){
		MasterData masterData = (MasterData) getSession().createQuery("from MasterData where jobId = :jobId and clientId = :clientId")
				.setParameter("jobId", jobId)
				.setParameter("clientId", clientId)
				.uniqueResult();
		return masterData;
	}
	
	/**
	 * Update master data job status
	 * @param jobId
	 * @param clientId
	 * @param status
	 * @return
	 */
	public Integer updateJob(String jobId, String clientId, String status){
		String Update_HQL_Query = "update MasterData set jobStatus = :status where jobId=:jobId and clientId=:clientId";
		Query query = getSession().createQuery(Update_HQL_Query);
		query.setString("jobId", jobId);
		query.setString("clientId", clientId);
		query.setString("status", status);
		int rowCount = query.executeUpdate();
		return rowCount;
	}
	
	/**
	 * Update chapter job status
	 * @param jobId
	 * @param status
	 * @param chapter
	 * @return
	 */
	public Integer updateChapterJobStatus(String jobId, String status, String chapter){
		String Update_HQL_Query = "update ChapterDetails set status = :status where jobId=:jobId and chapterName=:chapter";
		Query query = getSession().createQuery(Update_HQL_Query);
		query.setString("jobId", jobId);
		query.setString("chapter", chapter);
		query.setString("status", status);
		int rowCount = query.executeUpdate();
		return rowCount;
	}
	
	/**
	 * Check failed jobs exists
	 * @param clientList
	 * @param jobList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MasterData> checkFailedJobs(List<String> clientList, List<String> jobList){
		String HQL_FAILED_JOBS = "from MasterData where clientId in (:clientId) and jobId in (:jobId) and jobStatus in (:status)";
		List<MasterData> failedJobs = getSession().createQuery(HQL_FAILED_JOBS)
										.setParameterList("clientId", clientList)
										.setParameterList("jobId", jobList)
										.setParameter("status", "FAILED")
										.list();
		if(!failedJobs.isEmpty()){
			return failedJobs;
		}
		return null;
	}
	
	/**
	 * Update failed jobs master data
	 * @param clientList
	 * @param jobList
	 * @param list
	 * @return
	 */
	public int updateFailedJobs(List<String> clientList, List<String> jobList, List<MasterData> list){
		int updatedCount = 0;
		for(MasterData data : list){
			Query query = getSession().createQuery("update MasterData set sId=:serialNo,stage1CopyEdit=:copyEditPath,stage2Graphics=:graphicsPath,stage3Equations=:equationsPath,stage4TemplateName=:templateName,stage5TemplatePath=:templatePath,stage6MaestroMappingPath=:maestroPath,Stage7StandardGenericStyleSheet=:styleSheetPath,noOfManuscripts=:manuscripts,jobStatus = :status, isNewJob = :isNewJob, modifiedOn = :modifiedOn where clientId in (:clientList) and jobId in (:jobList)");
			/*query.setString("copyEditPath", data.getStage1CopyEdit());
			query.setString("graphicsPath", data.getStage2Graphics());
			query.setString("equationsPath", data.getStage3Equations());*/
			query.setString("templateName", data.getStage4TemplateName());
			/*query.setString("templatePath", data.getStage5TemplatePath());*/
			/*query.setString("maestroPath", data.getStage6MaestroMappingPath());*/
			/*query.setString("styleSheetPath", data.getStage7StandardGenericStyleSheet());*/
			query.setInteger("manuscripts", data.getNoOfManuscripts());
			query.setParameter("status", "IN-PROGRESS");
			query.setBoolean("isNewJob", false);
			query.setString("clientList", data.getClientId());
			query.setString("jobList", data.getJobId());
			query.setString("serialNo", data.getsId());
			query.setTimestamp("modifiedOn", date);
			//query.setParameterList("clientList", clientList);
			//query.setParameterList("jobList", jobList);
			updatedCount = query.executeUpdate();
		}
		return updatedCount;
	}
	
	/**
	 * Check duplicate jobs exists
	 * by isbn 
	 * @param isbnList
	 * @return
	 */
	public List<MasterData> checkDuplicateJobs(List<String> isbnList){
		List<MasterData> duplicatejobIsbn = getSession().createQuery("from MasterData where job_isbn in (:jobIsbn) and jobStatus not in (:status)")
											 .setParameterList("jobIsbn", isbnList)
											 .setParameter("status", "FAILED")
											.list();
		return duplicatejobIsbn;
	}
	
	public List<ClientDetails> findByClient(String clientId){
		@SuppressWarnings("unchecked")
		List<String> client = getSession().createQuery("select distinct(clientId) from MasterData where lower(clientId) like :data")
							.setParameter("data", clientId + "%")
							.list();
		
		List<ClientDetails> clientName = new ArrayList<ClientDetails>();
		ClientDetails clients = null;
		for(String d : client){
			clients = new ClientDetails();
			clients.setClientName(d);
			clientName.add(clients);
		}
		return clientName;
	}
	
	/**
	 * Check serialNo duplicate exists
	 * by isbn 
	 * @param isbnList
	 * @return
	 */
	public List<MasterData> checkDuplicateSerialNo(List<String> serialNoList){
		List<MasterData> duplicateSerialNo = getSession().createQuery("from MasterData where job_serial_no in (:serialNo) and jobStatus not in (:status)")
											 .setParameterList("serialNo", serialNoList)
											 .setParameter("status", "FAILED")
											.list();
		return duplicateSerialNo;
	}
	
	/**
	 * Update master data status
	 * for manuscripts
	 * @param jobId
	 * @param clientId
	 * @param status
	 * @return
	 */
	public Integer updateJobStatus(String jobId, String clientId){
		String Update_HQL_Query = "update MasterData set jobStatus = :status where jobId=:jobId and clientId=:clientId";
		Query query = getSession().createQuery(Update_HQL_Query);
		query.setString("jobId", jobId);
		query.setString("clientId", clientId);
		query.setString("status", "IN-PROGRESS");
		int rowCount = query.executeUpdate();
		return rowCount;
	}
	
	/**
	 * Update master data manuscripts
	 * @param jobId
	 * @param clientId
	 * @param status
	 * @return
	 */
	public Integer updateManuscripts(String jobId, String clientId, Integer manuscripts){
		String Update_HQL_Query = "update MasterData set noOfManuscripts = :manuscripts where jobId=:jobId and clientId=:clientId";
		Query query = getSession().createQuery(Update_HQL_Query);
		query.setString("jobId", jobId);
		query.setString("clientId", clientId);
		query.setInteger("manuscripts", manuscripts);
		int rowCount = query.executeUpdate();
		return rowCount;
	}
}
