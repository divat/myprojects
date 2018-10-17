package com.codemantra.maestro.service;

import java.util.List;

import com.codemantra.maestro.model.ChapterDetails;
import com.codemantra.maestro.model.MasterData;

public interface ChapterDetailsService {

	ChapterDetails getChapterByIdAndStage(String jobId, String chapter, String stage1);
	
	ChapterDetails isChapterDetailsExists(String jobId, String chapterId);
	
	Integer update(ChapterDetails details);
	
	Integer updateStatus(ChapterDetails details);
	
	List<ChapterDetails> updateReadyStatus(List<ChapterDetails> chapter, String chapterDate, String styleSheetModifiedDate);
	
	List<ChapterDetails> checkChapterDetailsExists(MasterData job);
	
	Integer updateTemplateStatus(ChapterDetails details);
	
	List<ChapterDetails> chapterList(Long id);
	
	Integer updateEquation(ChapterDetails details, boolean isEquationExists);
	
	Integer updateWordMapStatus(ChapterDetails details, boolean wordMapStatus);
	
	Integer updateChapter(boolean cleanUp, boolean docVal, boolean strucVal, boolean postVal, boolean postConv, boolean maestroCert, Long chapterId);
	
	List<ChapterDetails> loadChapterDetails(String chapterName);
	
	Long getChapterDetailsCount(String jobId);
}
