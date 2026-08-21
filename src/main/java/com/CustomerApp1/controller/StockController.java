package com.CustomerApp1.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.CustomerApp1.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.CustomerApp1.domain.Stock;
import com.CustomerApp1.dto.StockDTO;
import com.CustomerApp1.dto.StockSearchDTO;
import com.CustomerApp1.dto.StockPageDTO;
import com.CustomerApp1.service.StockService;
import com.CustomerApp1.dto.common.RequestDTO;
import com.CustomerApp1.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/stock")
@RestController
public class StockController {

	private final static Logger logger = LoggerFactory.getLogger(StockController.class);

	@Autowired
	StockService stockService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Stock> getAll() {

		List<Stock> stocks = stockService.findAll();
		
		return stocks;	
	}

	@GetMapping(value = "/{stockId}")
	@ResponseBody
	public StockDTO getStock(@PathVariable Integer stockId) {
		
		return (stockService.getStockDTOById(stockId));
	}

 	@RequestMapping(value = "/addStock", method = RequestMethod.POST)
	public ResponseEntity<?> addStock(@RequestBody StockDTO stockDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = stockService.addStock(stockDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/stocks")
	public ResponseEntity<StockPageDTO> getStocks(StockSearchDTO stockSearchDTO) {
 
		return stockService.getStocks(stockSearchDTO);
	}	

	@RequestMapping(value = "/updateStock", method = RequestMethod.POST)
	public ResponseEntity<?> updateStock(@RequestBody StockDTO stockDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = stockService.updateStock(stockDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
