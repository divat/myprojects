package com.codemantra.maestro.dao;

import org.springframework.stereotype.Repository;

import com.codemantra.maestro.config.hibernate.Dao;
import com.codemantra.maestro.model.FileMetadata;

@Repository
public class MetadataDao extends Dao<FileMetadata>{

	public MetadataDao(){
		super(FileMetadata.class);
	}
}
