package com.codemantra.maestro.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.codemantra.maestro.config.hibernate.Dao;
import com.codemantra.maestro.model.ChapterDetails;

@Repository
@Transactional
public class ChapterDetailsDao extends Dao<ChapterDetails>{

	/*private static final Date utilDate = new Date();
	private static final Timestamp date = new Timestamp(utilDate.getTime());*/
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public ChapterDetailsDao(){
		super(ChapterDetails.class);
	}
	
	/**
	 * Find job by job-id and chapter
	 * @param jobId
	 * @param chapterId
	 * @return
	 */
	public ChapterDetails findByJobAndChapter(String jobId, String chapterId){
		ChapterDetails chapterDetails = (ChapterDetails) getSession().createQuery("from ChapterDetails d where d.metadataJobId.id = :jobId and chapterName = :chapter")
				.setParameter("jobId", Long.valueOf(jobId))
				.setParameter("chapter", chapterId)
				.uniqueResult();
		return chapterDetails;
	}
	
	/**
	 * Find job by chapter and stage
	 * @param jobId
	 * @param chapterId
	 * @param stage1
	 * @return
	 */
	public ChapterDetails findByJobChapterStage(String jobId, String chapterId, String stage1){
		ChapterDetails chapterDetails = (ChapterDetails) getSession().createQuery("from ChapterDetails d where d.metadataJobId.id = :jobId and chapterName = :chapter and stage1 = :stage1")
				.setParameter("jobId", Long.valueOf(jobId))
				.setParameter("chapter", chapterId)
				.setBoolean("stage1", Boolean.valueOf(stage1))
				.uniqueResult();
		return chapterDetails;
	}
	
	/**
	 * Update chapter for pre-editing process
	 * @param details
	 * @return
	 */
	public Integer updateChapter(ChapterDetails details){
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		String HQL = "update ChapterDetails set stageCleanUp =:stage1,docValidation=:stage3,structuringVal=:stage4,postVal=:stage5,postConv=:stage6,modifiedOn=:modifiedOn where chapterName = :chapterId";
		Query query = getSession().createQuery(HQL);
		query.setString("chapterId", details.getChapterName());
		query.setBoolean("stage1", details.getStageCleanUp());
		//query.setBoolean("stage2", details.getPreEditStage());
		query.setBoolean("stage3", details.getDocValidation());
		query.setBoolean("stage4", details.getStructuringVal());
		query.setBoolean("stage5", details.getPostVal());
		query.setBoolean("stage6", details.getPostConv());
		query.setTimestamp("modifiedOn", date);
		int rowCount = query.executeUpdate();
		return rowCount;
	}
	
	/**
	 * Update the chapter details for indesign process
	 * @param details
	 * @return
	 */
	public Integer updateChapterStyleStage(ChapterDetails details){
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		String HQL = "update ChapterDetails set inDStyleMapStatus=:status,modifiedOn=:modifiedOn where chapterName = :chapterId";
		Query query = getSession().createQuery(HQL);
		query.setString("chapterId", details.getChapterName());
		query.setBoolean("status", details.getInDStyleMapStatus());
		query.setTimestamp("modifiedOn", date);
		int rowCount = query.executeUpdate();
		return rowCount;
	}
	
	/**
	 * 
	 * @param details
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ChapterDetails> findChaptersByJob(Long id){
		List<ChapterDetails> list = getSession().createQuery("from ChapterDetails d where d.metadataJobId.id = :id")
				.setParameter("id", id)
				.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ChapterDetails> checkChapterStage(Long id){
		List<ChapterDetails> list = getSession().createQuery("from ChapterDetails d where d.metadataJobId.id = :id and "
				+ " d.stageCleanUp=true and d.docValidation=true and d.structuringVal=true and d.postVal=true and d.postConv=true and "
				+ " d.stageWsStyleChecking=true and d.stageWsInDImportMap=true")
				.setParameter("id", id)
				.list();
		return list;
	}
	
	public Integer updateChapterStatus(List<Long> chapters, String chapterDate, String styleSheetModifiedDate) throws ParseException{
		String UPDATE_CHAPTER_STATUS_HQL = "update ChapterDetails d set status = :status, chapterModifiedDate=:chModifiedDate, styleSheetModifiedDate=:styleSheetModifiedDate where id in (:chapterId)";
		Query query = getSession().createQuery(UPDATE_CHAPTER_STATUS_HQL);
		query.setBoolean("status", true);
		query.setParameterList("chapterId", chapters);
		
		DateFormat  format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = format.parse(chapterDate);
		query.setTimestamp("chModifiedDate", date);
		
		DateFormat formatStlyleSheetDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date stlyleSheetDate1 = formatStlyleSheetDate.parse(styleSheetModifiedDate);
		query.setTimestamp("styleSheetModifiedDate", stlyleSheetDate1);
		
		int count = query.executeUpdate();
		return count;
	}
	
	/**
	 * Update the chapter status and comments
	 * @param details
	 * @return
	 */
	public Integer updateChapterTemplateStatus(ChapterDetails details){
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		String HQL = "update ChapterDetails set inDStyleMapStatus=:status,inDStyleMapComments=:comments,modifiedOn=:modifiedOn where chapterName = :chapterId";
		Query query = getSession().createQuery(HQL);
		query.setString("chapterId", details.getChapterName());
		query.setBoolean("status", details.getInDStyleMapStatus());
		query.setString("comments", details.getInDStyleMapComments());
		/*query.setBoolean("stage1", details.getStageWsStyleChecking());
		query.setBoolean("stage2", details.getStageWsInDImportMap());*/
		query.setTimestamp("modifiedOn", date);
		int rowCount = query.executeUpdate();
		return rowCount;
	}
	
	/**
	 * Update the chapter to ensure equation
	 * exists
	 * @param details
	 * @param isEquationExists
	 * @return
	 */
	public Integer updateEquation(ChapterDetails details, boolean isEquationExists){
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		String UPDATE_EQUATION = "update ChapterDetails set mEquation=:value,modifiedOn=:modifiedOn  where id = :chapterId";
		Query query = getSession().createQuery(UPDATE_EQUATION);
		query.setLong("chapterId", details.getId());
		query.setBoolean("value", isEquationExists);
		query.setTimestamp("modifiedOn", date);
		int update = query.executeUpdate();
		return update;
	}
	
	/**
	 * Update the chapter to ensure equation
	 * exists
	 * @param details
	 * @param isEquationExists
	 * @return
	 */
	public Integer updateWordMapStatus(ChapterDetails details, boolean wordMapStatus){
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		String UPDATE_EQUATION = "update ChapterDetails set modifiedOn=:modifiedOn, wordExportMapStatus=:wordMapStatus where id = :chapterId";
		Query query = getSession().createQuery(UPDATE_EQUATION);
		query.setLong("chapterId", details.getId());
		query.setBoolean("wordMapStatus", wordMapStatus);
		query.setTimestamp("modifiedOn", date);
		int update = query.executeUpdate();
		return update;
	}

	@SuppressWarnings("unchecked")
	public List<ChapterDetails> chapterList(Long id){
		List<ChapterDetails> list = getSession().createQuery("from ChapterDetails d where d.metadataJobId.id = :id")
				.setParameter("id", id)
				.list();
		return list;
	}
	
	public Integer updateChapterStatus(boolean cleanUp, boolean docVal, boolean strucVal, boolean postVal, boolean postConv,
			boolean wordExportStatus, Long chapterId){
		Date utilDate = new Date();
		Timestamp date = new Timestamp(utilDate.getTime());
		String UPDATE_EQUATION = "update ChapterDetails set modifiedOn=:modifiedOn,stageCleanUp=:cleanUp,docValidation=:docVal,structuringVal=:strucVal,postVal=:postVal,postConv=:postConv,wordExportMapStatus=:wordExportStatus where id = :chapterId";
		Query query = getSession().createQuery(UPDATE_EQUATION);
		query.setLong("chapterId", chapterId);
		query.setBoolean("cleanUp", cleanUp);
		query.setBoolean("docVal", docVal);
		query.setBoolean("strucVal", strucVal);
		query.setBoolean("postVal", postVal);
		query.setBoolean("postConv", postConv);
		query.setBoolean("wordExportStatus", wordExportStatus);
		query.setTimestamp("modifiedOn", date);
		int update = query.executeUpdate();
		return update;
	}
	
	public Long getChapterCount(String jobId){
		String HQL_CHAPTER_COUNT_QUERY = "select count(*) from ChapterDetails d where d.metadataJobId.id = :id";
		Query query = getSession().createQuery(HQL_CHAPTER_COUNT_QUERY)
				.setParameter("id", Long.valueOf(jobId));
		return (Long) query.uniqueResult();
	}
}
