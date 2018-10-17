package com.codemantra.maestro.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	List<String> uploadJob(MultipartFile file);
}
