package com.CustomerApp1.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StockSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer stockId;
	
	private String tickerSymbol;
	
	private String companyName;
	
	private double marketValue;
	
}
