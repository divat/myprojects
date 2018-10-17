package com.codemantra.maestro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemantra.maestro.dao.ChapterDetailsDao;
import com.codemantra.maestro.model.ChapterDetails;
import com.codemantra.maestro.model.MasterData;

@Service
public class ChapterDetailsServiceImpl implements ChapterDetailsService{

	@Autowired
	ChapterDetailsDao chapterDetailsDao;
	
	@Override
	public ChapterDetails isChapterDetailsExists(String jobId, String chapterId) {
		return chapterDetailsDao.findByJobAndChapter(jobId, chapterId);
	}

	@Override
	public Integer update(ChapterDetails details) {
		return chapterDetailsDao.updateChapter(details);
	}

	@Override
	public ChapterDetails getChapterByIdAndStage(String jobId,String chapter,String stage1) {
		return chapterDetailsDao.findByJobChapterStage(jobId, chapter, stage1);
	}

	@Override
	public Integer updateStatus(ChapterDetails details) {
		return chapterDetailsDao.updateChapterStyleStage(details);
	}

	@Override
	public List<ChapterDetails> updateReadyStatus(List<ChapterDetails> chapters, String chapterDate, String styleSheetModifiedDate) {
		List<ChapterDetails> chaptersPassedList = new ArrayList<ChapterDetails>(); 
		List<ChapterDetails> chaptersFailList = new ArrayList<ChapterDetails>();
		List<Long> chapterIdList = new ArrayList<Long>();
		try {
			if(chapters != null && !chapters.isEmpty()){
				for(ChapterDetails d : chapters){
					if(d.getStageCleanUp() == true && d.getDocValidation() == true && d.getStructuringVal() == true && d.getPostVal() == true && d.getPostConv() == true 
							&& d.getInDStyleMapStatus() == true){
						chaptersPassedList.add(d);
					}else{
						chaptersFailList.add(d);
					}
				}
				
				if(chaptersPassedList != null && !chaptersPassedList.isEmpty()){
					//update chapters status
					for(ChapterDetails id : chaptersPassedList){
						chapterIdList.add(id.getId());
					}
					chapterDetailsDao.updateChapterStatus(chapterIdList, chapterDate, styleSheetModifiedDate);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chaptersFailList;
	}

	@Override
	public List<ChapterDetails> checkChapterDetailsExists(MasterData job) {
		return chapterDetailsDao.findChaptersByJob(job.getId());
	}

	@Override
	public Integer updateTemplateStatus(ChapterDetails details) {
		return chapterDetailsDao.updateChapterTemplateStatus(details);
	}

	@Override
	public List<ChapterDetails> chapterList(Long id) {
		return chapterDetailsDao.chapterList(id);
	}

	@Override
	public Integer updateEquation(ChapterDetails details, boolean isEquationExists) {
		return chapterDetailsDao.updateEquation(details, isEquationExists);
	}

	@Override
	public Integer updateWordMapStatus(ChapterDetails details, boolean wordMapStatus) {
		return chapterDetailsDao.updateWordMapStatus(details, wordMapStatus);
	}

	@Override
	public Integer updateChapter(boolean cleanUp, boolean docVal, boolean strucVal, boolean postVal, boolean postConv,
			boolean maestroCert, Long chapterId) {
		return chapterDetailsDao.updateChapterStatus(cleanUp, docVal, strucVal, postVal, postConv, maestroCert, chapterId);
	}

	@Override
	public List<ChapterDetails> loadChapterDetails(String chapterName) {
		return chapterDetailsDao.findByString("chapterName", chapterName);
	}

	@Override
	public Long getChapterDetailsCount(String jobId) {
		return chapterDetailsDao.getChapterCount(jobId);
	}

}