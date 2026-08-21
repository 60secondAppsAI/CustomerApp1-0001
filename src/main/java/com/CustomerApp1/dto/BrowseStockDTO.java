package com.CustomerApp1.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseStockDTO {

	private Integer ownerId;

	private Integer stockId;

	private Integer stockStatus;
	
	private Integer nextOrPrevious;
}

