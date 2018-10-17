package com.codemantra.maestro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemantra.maestro.dao.ChapterValDao;
import com.codemantra.maestro.dao.MasterDataDao;
import com.codemantra.maestro.model.ChapterDetails;
import com.codemantra.maestro.model.ClientDetails;
import com.codemantra.maestro.model.MasterData;

@Service
public class MasterDataServiceImpl implements MasterDataService{

	@Autowired
	MasterDataDao masterDataDao;
	
	@Autowired
	ChapterValDao chapterValDao;
	
	@Override
	public List<MasterData> checkJobExists(List<String> clientIdList, List<String> jobIdList) {
		List<MasterData> list = masterDataDao.checkDuplicateJobs(clientIdList, jobIdList);
		return list;
	}

	@Override
	public MasterData checkJobExists(String jobId, String clientId) {
		MasterData masterData = masterDataDao.findByJobAndClient(jobId, clientId);
		return masterData;
	}

	@Override
	public ChapterDetails saveChapterInfo(ChapterDetails obj) {
		ChapterDetails details = chapterValDao.save(obj);
		return details;
	}

	@Override
	public List<MasterData> jobList(String status, String value) {
		return masterDataDao.findByStringField(status, value);
	}

	@Override
	public int updateJob(String jobId, String clientId, String status) {
		return masterDataDao.updateJob(jobId, clientId, status);
	}

	@Override
	public int updateChapterJobStatus(String jobId, String status, String chapter) {
		return masterDataDao.updateChapterJobStatus(jobId, status, chapter);
	}

	@Override
	public List<MasterData> jobList(String username) {
		return masterDataDao.findByStringField("createdBy", username);
	}

	@Override
	public List<MasterData> checkFailedJobExists(List<String> clientIdList, List<String> jobIdList) {
		return masterDataDao.checkFailedJobs(clientIdList, jobIdList);
	}

	@Override
	public int updateFailedJobs(List<String> clientIdList, List<String> jobIdList, List<MasterData> list) {
		return masterDataDao.updateFailedJobs(clientIdList, jobIdList, list);
	}

	@Override
	public List<MasterData> checkJobIsbnExists(List<String> isbnList) {
		List<MasterData> list = masterDataDao.checkDuplicateJobs(isbnList);
		return list;
	}

	@Override
	public List<MasterData> loadJob(String jobId) {
		return masterDataDao.findByString("jobId", jobId);
	}	
	
	@Override
	public List<ClientDetails> loadClient(String clientId) {
		return masterDataDao.findByClient(clientId);
	}

	@Override
	public int updateJobPath(MasterData masterData) {
		MasterData masterDataObj = masterDataDao.findById(masterData.getId());
		if(masterDataObj != null){
			masterDataObj = masterDataDao.update(masterData);
			return 1;
		}
		return 0;
		
	}
	
	@Override
	public List<MasterData> checkSerialNoExists(List<String> serialNoList) {
		List<MasterData> list = masterDataDao.checkDuplicateSerialNo(serialNoList);
		return list;
	}

	@Override
	public int updateJobStatus(String jobId, String clientId) {
		int jobStatusCount = masterDataDao.updateJobStatus(jobId, clientId);
		return jobStatusCount;
	}

	@Override
	public int updateManuscripts(String jobId, String clientId, Integer manuscripts) {
		int manuscriptsUpdateCount = masterDataDao.updateManuscripts(jobId, clientId, manuscripts);
		return manuscriptsUpdateCount;
	}

}