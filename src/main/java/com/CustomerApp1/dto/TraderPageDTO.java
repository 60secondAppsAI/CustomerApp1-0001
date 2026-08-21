package com.CustomerApp1.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TraderPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<TraderDTO> traders;
}





