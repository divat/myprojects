package com.cm.cs.api.service;

import java.util.List;

import com.cm.cs.api.model.Division;

public interface IDivisionService {

	List<Division> divisionList();
	
	Division findByDivision(Long divisionId);
}
