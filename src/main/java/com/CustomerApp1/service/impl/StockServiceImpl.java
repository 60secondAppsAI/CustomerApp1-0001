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
import com.CustomerApp1.dao.StockDAO;
import com.CustomerApp1.domain.Stock;
import com.CustomerApp1.dto.StockDTO;
import com.CustomerApp1.dto.StockSearchDTO;
import com.CustomerApp1.dto.StockPageDTO;
import com.CustomerApp1.dto.StockConvertCriteriaDTO;
import com.CustomerApp1.dto.common.RequestDTO;
import com.CustomerApp1.dto.common.ResultDTO;
import com.CustomerApp1.service.StockService;
import com.CustomerApp1.util.ControllerUtils;


@Service
public class StockServiceImpl extends GenericServiceImpl<Stock, Integer> implements StockService {

    private final static Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

	@Autowired
	StockDAO stockDao;

	

	@Override
	public GenericDAO<Stock, Integer> getDAO() {
		return (GenericDAO<Stock, Integer>) stockDao;
	}
	
	public List<Stock> findAll () {
		List<Stock> stocks = stockDao.findAll();
		
		return stocks;	
		
	}

	public ResultDTO addStock(StockDTO stockDTO, RequestDTO requestDTO) {

		Stock stock = new Stock();

		stock.setStockId(stockDTO.getStockId());

		stock.setTickerSymbol(stockDTO.getTickerSymbol());

		stock.setCompanyName(stockDTO.getCompanyName());

		stock.setMarketValue(stockDTO.getMarketValue());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		stock = stockDao.save(stock);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Stock> getAllStocks(Pageable pageable) {
		return stockDao.findAll(pageable);
	}

	public Page<Stock> getAllStocks(Specification<Stock> spec, Pageable pageable) {
		return stockDao.findAll(spec, pageable);
	}

	public ResponseEntity<StockPageDTO> getStocks(StockSearchDTO stockSearchDTO) {
	
			Integer stockId = stockSearchDTO.getStockId(); 
 			String tickerSymbol = stockSearchDTO.getTickerSymbol(); 
 			String companyName = stockSearchDTO.getCompanyName(); 
  			String sortBy = stockSearchDTO.getSortBy();
			String sortOrder = stockSearchDTO.getSortOrder();
			String searchQuery = stockSearchDTO.getSearchQuery();
			Integer page = stockSearchDTO.getPage();
			Integer size = stockSearchDTO.getSize();

	        Specification<Stock> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, stockId, "stockId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, tickerSymbol, "tickerSymbol"); 
			
			spec = ControllerUtils.andIfNecessary(spec, companyName, "companyName"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("tickerSymbol")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("companyName")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Stock> stocks = this.getAllStocks(spec, pageable);
		
		//System.out.println(String.valueOf(stocks.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(stocks.getTotalPages()));
		
		List<Stock> stocksList = stocks.getContent();
		
		StockConvertCriteriaDTO convertCriteria = new StockConvertCriteriaDTO();
		List<StockDTO> stockDTOs = this.convertStocksToStockDTOs(stocksList,convertCriteria);
		
		StockPageDTO stockPageDTO = new StockPageDTO();
		stockPageDTO.setStocks(stockDTOs);
		stockPageDTO.setTotalElements(stocks.getTotalElements());
		return ResponseEntity.ok(stockPageDTO);
	}

	public List<StockDTO> convertStocksToStockDTOs(List<Stock> stocks, StockConvertCriteriaDTO convertCriteria) {
		
		List<StockDTO> stockDTOs = new ArrayList<StockDTO>();
		
		for (Stock stock : stocks) {
			stockDTOs.add(convertStockToStockDTO(stock,convertCriteria));
		}
		
		return stockDTOs;

	}
	
	public StockDTO convertStockToStockDTO(Stock stock, StockConvertCriteriaDTO convertCriteria) {
		
		StockDTO stockDTO = new StockDTO();

		stockDTO.setStockId(stock.getStockId());

		stockDTO.setTickerSymbol(stock.getTickerSymbol());

		stockDTO.setCompanyName(stock.getCompanyName());

		stockDTO.setMarketValue(stock.getMarketValue());
		
		return stockDTO;
	}

	public ResultDTO updateStock(StockDTO stockDTO, RequestDTO requestDTO) {
		
		Stock stock = stockDao.getById(stockDTO.getStockId());
		
		stock.setStockId(ControllerUtils.setValue(stock.getStockId(), stockDTO.getStockId()));
		
		stock.setTickerSymbol(ControllerUtils.setValue(stock.getTickerSymbol(), stockDTO.getTickerSymbol()));
		
		stock.setCompanyName(ControllerUtils.setValue(stock.getCompanyName(), stockDTO.getCompanyName()));
		
		stock.setMarketValue(ControllerUtils.setValue(stock.getMarketValue(), stockDTO.getMarketValue()));

        stock = stockDao.save(stock);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public StockDTO getStockDTOById(Integer stockId) {
	
		Stock stock = stockDao.getById(stockId);
		
		StockConvertCriteriaDTO convertCriteria = new StockConvertCriteriaDTO();
		return(this.convertStockToStockDTO(stock,convertCriteria));
	}

}
