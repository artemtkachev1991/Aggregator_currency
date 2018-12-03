package com.example.currency.aggregation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CurrencyAggregationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyAggregationApplication.class, args);
	}
}
