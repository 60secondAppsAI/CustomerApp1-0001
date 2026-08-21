package com.CustomerApp1.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.CustomerApp1.domain.Trader;
import com.CustomerApp1.dto.TraderDTO;
import com.CustomerApp1.dto.TraderSearchDTO;
import com.CustomerApp1.dto.TraderPageDTO;
import com.CustomerApp1.dto.TraderConvertCriteriaDTO;
import com.CustomerApp1.service.GenericService;
import com.CustomerApp1.dto.common.RequestDTO;
import com.CustomerApp1.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TraderService extends GenericService<Trader, Integer> {

	List<Trader> findAll();

	ResultDTO addTrader(TraderDTO traderDTO, RequestDTO requestDTO);

	ResultDTO updateTrader(TraderDTO traderDTO, RequestDTO requestDTO);

    Page<Trader> getAllTraders(Pageable pageable);

    Page<Trader> getAllTraders(Specification<Trader> spec, Pageable pageable);

	ResponseEntity<TraderPageDTO> getTraders(TraderSearchDTO traderSearchDTO);
	
	List<TraderDTO> convertTradersToTraderDTOs(List<Trader> traders, TraderConvertCriteriaDTO convertCriteria);

	TraderDTO getTraderDTOById(Integer traderId);



	
}
