package com.codemantra.maestro.service;

import java.util.List;
import java.util.Set;

import com.codemantra.maestro.model.ChapterDetails;
import com.codemantra.maestro.model.ClientDetails;
import com.codemantra.maestro.model.MasterData;

public interface MasterDataService {

	List<MasterData> jobList(String jobStatus, String value);
	
	List<MasterData> checkJobExists(List<String> clientIdList, List<String> jobIdList);
	
	MasterData checkJobExists(String jobId, String clientId);
	
	ChapterDetails saveChapterInfo(ChapterDetails obj);
	
	int updateJob(String jobId, String clientId, String status);
	
	int updateChapterJobStatus(String jobId, String clientId, String status);
	
	List<MasterData> jobList(String username);
	
	List<MasterData> checkFailedJobExists(List<String> clientIdList, List<String> jobIdList);
	
	int updateFailedJobs(List<String> clientIdList, List<String> jobIdList, List<MasterData> list);
	
	List<MasterData> checkJobIsbnExists(List<String> isbnList);
	
	List<MasterData> loadJob(String jobId);
	
	List<ClientDetails> loadClient(String clientId);
	
	int updateJobPath(MasterData masterData);
	
	List<MasterData> checkSerialNoExists(List<String> serialNoList);
	
	int updateJobStatus(String jobId, String clientId);
	
	int updateManuscripts(String jobId, String clientId, Integer manuscripts);
}
