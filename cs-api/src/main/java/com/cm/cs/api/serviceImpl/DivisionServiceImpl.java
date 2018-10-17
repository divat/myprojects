package com.cm.cs.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.cs.api.dao.DivisionDao;
import com.cm.cs.api.model.Division;
import com.cm.cs.api.service.IDivisionService;

@Service
public class DivisionServiceImpl implements IDivisionService{

	@Autowired
	DivisionDao divisionDao;
	
	@Override
	public List<Division> divisionList() {
		return divisionDao.findAll();
	}

	@Override
	public Division findByDivision(Long divisionId) {
		return divisionDao.findById(divisionId);
	}

}
