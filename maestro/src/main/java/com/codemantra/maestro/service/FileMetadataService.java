package com.codemantra.maestro.service;

import com.codemantra.maestro.model.FileMetadata;

public interface FileMetadataService {

	FileMetadata findByMetadataId(Long id);
}
