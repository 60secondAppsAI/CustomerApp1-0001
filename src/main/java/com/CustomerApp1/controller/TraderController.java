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

import com.CustomerApp1.domain.Trader;
import com.CustomerApp1.dto.TraderDTO;
import com.CustomerApp1.dto.TraderSearchDTO;
import com.CustomerApp1.dto.TraderPageDTO;
import com.CustomerApp1.service.TraderService;
import com.CustomerApp1.dto.common.RequestDTO;
import com.CustomerApp1.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/trader")
@RestController
public class TraderController {

	private final static Logger logger = LoggerFactory.getLogger(TraderController.class);

	@Autowired
	TraderService traderService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Trader> getAll() {

		List<Trader> traders = traderService.findAll();
		
		return traders;	
	}

	@GetMapping(value = "/{traderId}")
	@ResponseBody
	public TraderDTO getTrader(@PathVariable Integer traderId) {
		
		return (traderService.getTraderDTOById(traderId));
	}

 	@RequestMapping(value = "/addTrader", method = RequestMethod.POST)
	public ResponseEntity<?> addTrader(@RequestBody TraderDTO traderDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = traderService.addTrader(traderDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/traders")
	public ResponseEntity<TraderPageDTO> getTraders(TraderSearchDTO traderSearchDTO) {
 
		return traderService.getTraders(traderSearchDTO);
	}	

	@RequestMapping(value = "/updateTrader", method = RequestMethod.POST)
	public ResponseEntity<?> updateTrader(@RequestBody TraderDTO traderDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = traderService.updateTrader(traderDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
