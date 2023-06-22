package com.currency.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.converters.Auto;

@RestController
public class CurrencyExchangeController {
	@Autowired
	private Environment env;
    @Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;

	@GetMapping("/currency-exchange/{from}/to/{to}")
	public CurrencyExchange getExchangeValue(@PathVariable String from, @PathVariable String to) {
		CurrencyExchange currencies = currencyExchangeRepository.findByFromAndTo(from, to);
		currencies.setEnvironment(env.getProperty("local.server.port"));

		return currencies;

	}

}
