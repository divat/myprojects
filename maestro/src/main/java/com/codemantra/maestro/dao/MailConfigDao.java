package com.codemantra.maestro.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codemantra.maestro.config.hibernate.Dao;
import com.codemantra.maestro.model.MailConfig;

@Repository
@Transactional
public class MailConfigDao extends Dao<MailConfig>{

	public MailConfigDao(){
		super(MailConfig.class);
	}
}
