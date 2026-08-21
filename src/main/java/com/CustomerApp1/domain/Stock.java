package com.CustomerApp1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="stocks")
@Getter @Setter @NoArgsConstructor
public class Stock {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="stock_id")
	private Integer stockId;
    
  	@Column(name="ticker_symbol")
	private String tickerSymbol;
    
  	@Column(name="company_name")
	private String companyName;
    
  	@Column(name="market_value")
	private double marketValue;
    

}
