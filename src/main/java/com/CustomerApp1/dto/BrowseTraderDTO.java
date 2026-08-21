package com.CustomerApp1.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseTraderDTO {

	private Integer ownerId;

	private Integer traderId;

	private Integer traderStatus;
	
	private Integer nextOrPrevious;
}

