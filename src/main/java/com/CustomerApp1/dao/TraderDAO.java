package com.CustomerApp1.dao;

import java.util.List;
import java.util.Date;

import com.CustomerApp1.dao.GenericDAO;
import com.CustomerApp1.domain.Trader;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface TraderDAO extends GenericDAO<Trader, Integer> {
  
	List<Trader> findAll();
	


}

