CREATE DATABASE IF NOT EXISTS CustomerApp1;

CREATE TABLE CustomerApp1.stocks (
    `stock_id` INT AUTO_INCREMENT,
    `ticker_symbol` VARCHAR(255),
    `company_name` VARCHAR(255),
    `market_value` DOUBLE,
PRIMARY KEY (stock_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE CustomerApp1.traders (
    `trader_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `brokerage_firm` VARCHAR(255),
PRIMARY KEY (trader_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE CustomerApp1.transactions (
    `transaction_id` INT AUTO_INCREMENT,
    `transaction_date` DATETIME,
    `transaction_amount` DOUBLE,
PRIMARY KEY (transaction_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
