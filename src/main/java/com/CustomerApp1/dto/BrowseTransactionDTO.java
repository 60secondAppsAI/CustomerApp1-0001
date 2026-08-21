package com.CustomerApp1.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseTransactionDTO {

	private Integer ownerId;

	private Integer transactionId;

	private Integer transactionStatus;
	
	private Integer nextOrPrevious;
}

