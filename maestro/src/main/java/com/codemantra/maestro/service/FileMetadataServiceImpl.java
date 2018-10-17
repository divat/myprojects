package com.codemantra.maestro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemantra.maestro.dao.MetadataDao;
import com.codemantra.maestro.model.FileMetadata;

@Service
public class FileMetadataServiceImpl implements FileMetadataService{

	@Autowired
	MetadataDao metadataDao;
	
	@Override
	public FileMetadata findByMetadataId(Long id) {
		return metadataDao.findById(id);
	}

}
