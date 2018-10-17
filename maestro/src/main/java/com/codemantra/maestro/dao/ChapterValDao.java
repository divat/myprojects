package com.codemantra.maestro.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codemantra.maestro.config.hibernate.Dao;
import com.codemantra.maestro.model.ChapterDetails;

@Repository
@Transactional
public class ChapterValDao extends Dao<ChapterDetails>{
	
	public ChapterValDao(){
		super(ChapterDetails.class);
	}

}
