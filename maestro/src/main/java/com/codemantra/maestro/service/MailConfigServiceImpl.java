package com.codemantra.maestro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemantra.maestro.dao.MailConfigDao;
import com.codemantra.maestro.model.MailConfig;

@Service
public class MailConfigServiceImpl implements MailConfigService{

	@Autowired
	MailConfigDao mailConfigDao;	
	
	@Override
	public MailConfig getMailConfig(String groupName) {
		return mailConfigDao.findByStringValue("groupName", groupName);
	}

}
