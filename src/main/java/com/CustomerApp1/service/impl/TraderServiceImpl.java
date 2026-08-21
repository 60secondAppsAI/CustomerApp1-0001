package com.CustomerApp1.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.CustomerApp1.dao.GenericDAO;
import com.CustomerApp1.service.GenericService;
import com.CustomerApp1.service.impl.GenericServiceImpl;
import com.CustomerApp1.dao.TraderDAO;
import com.CustomerApp1.domain.Trader;
import com.CustomerApp1.dto.TraderDTO;
import com.CustomerApp1.dto.TraderSearchDTO;
import com.CustomerApp1.dto.TraderPageDTO;
import com.CustomerApp1.dto.TraderConvertCriteriaDTO;
import com.CustomerApp1.dto.common.RequestDTO;
import com.CustomerApp1.dto.common.ResultDTO;
import com.CustomerApp1.service.TraderService;
import com.CustomerApp1.util.ControllerUtils;


@Service
public class TraderServiceImpl extends GenericServiceImpl<Trader, Integer> implements TraderService {

    private final static Logger logger = LoggerFactory.getLogger(TraderServiceImpl.class);

	@Autowired
	TraderDAO traderDao;

	

	@Override
	public GenericDAO<Trader, Integer> getDAO() {
		return (GenericDAO<Trader, Integer>) traderDao;
	}
	
	public List<Trader> findAll () {
		List<Trader> traders = traderDao.findAll();
		
		return traders;	
		
	}

	public ResultDTO addTrader(TraderDTO traderDTO, RequestDTO requestDTO) {

		Trader trader = new Trader();

		trader.setTraderId(traderDTO.getTraderId());

		trader.setName(traderDTO.getName());

		trader.setBrokerageFirm(traderDTO.getBrokerageFirm());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		trader = traderDao.save(trader);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Trader> getAllTraders(Pageable pageable) {
		return traderDao.findAll(pageable);
	}

	public Page<Trader> getAllTraders(Specification<Trader> spec, Pageable pageable) {
		return traderDao.findAll(spec, pageable);
	}

	public ResponseEntity<TraderPageDTO> getTraders(TraderSearchDTO traderSearchDTO) {
	
			Integer traderId = traderSearchDTO.getTraderId(); 
 			String name = traderSearchDTO.getName(); 
 			String brokerageFirm = traderSearchDTO.getBrokerageFirm(); 
 			String sortBy = traderSearchDTO.getSortBy();
			String sortOrder = traderSearchDTO.getSortOrder();
			String searchQuery = traderSearchDTO.getSearchQuery();
			Integer page = traderSearchDTO.getPage();
			Integer size = traderSearchDTO.getSize();

	        Specification<Trader> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, traderId, "traderId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, brokerageFirm, "brokerageFirm"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("brokerageFirm")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Trader> traders = this.getAllTraders(spec, pageable);
		
		//System.out.println(String.valueOf(traders.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(traders.getTotalPages()));
		
		List<Trader> tradersList = traders.getContent();
		
		TraderConvertCriteriaDTO convertCriteria = new TraderConvertCriteriaDTO();
		List<TraderDTO> traderDTOs = this.convertTradersToTraderDTOs(tradersList,convertCriteria);
		
		TraderPageDTO traderPageDTO = new TraderPageDTO();
		traderPageDTO.setTraders(traderDTOs);
		traderPageDTO.setTotalElements(traders.getTotalElements());
		return ResponseEntity.ok(traderPageDTO);
	}

	public List<TraderDTO> convertTradersToTraderDTOs(List<Trader> traders, TraderConvertCriteriaDTO convertCriteria) {
		
		List<TraderDTO> traderDTOs = new ArrayList<TraderDTO>();
		
		for (Trader trader : traders) {
			traderDTOs.add(convertTraderToTraderDTO(trader,convertCriteria));
		}
		
		return traderDTOs;

	}
	
	public TraderDTO convertTraderToTraderDTO(Trader trader, TraderConvertCriteriaDTO convertCriteria) {
		
		TraderDTO traderDTO = new TraderDTO();

		traderDTO.setTraderId(trader.getTraderId());

		traderDTO.setName(trader.getName());

		traderDTO.setBrokerageFirm(trader.getBrokerageFirm());
		
		return traderDTO;
	}

	public ResultDTO updateTrader(TraderDTO traderDTO, RequestDTO requestDTO) {
		
		Trader trader = traderDao.getById(traderDTO.getTraderId());
		
		trader.setTraderId(ControllerUtils.setValue(trader.getTraderId(), traderDTO.getTraderId()));
		
		trader.setName(ControllerUtils.setValue(trader.getName(), traderDTO.getName()));
		
		trader.setBrokerageFirm(ControllerUtils.setValue(trader.getBrokerageFirm(), traderDTO.getBrokerageFirm()));

        trader = traderDao.save(trader);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public TraderDTO getTraderDTOById(Integer traderId) {
	
		Trader trader = traderDao.getById(traderId);
		
		TraderConvertCriteriaDTO convertCriteria = new TraderConvertCriteriaDTO();
		return(this.convertTraderToTraderDTO(trader,convertCriteria));
	}

}
