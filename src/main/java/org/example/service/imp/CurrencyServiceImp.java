package org.example.service.imp;

import org.example.dto.ExchangeDto;
import org.example.exceptions.NotFoundCurrencyException;
import org.example.service.CurrencyService;
import org.example.service.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


public class CurrencyServiceImp implements CurrencyService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CurrencyServiceImp.class);

    private final ExchangeService exchangeService;

    public CurrencyServiceImp(){
        this.exchangeService = new ExchangeServiceImp();
    }
    @Override
    public BigDecimal convert(BigDecimal amount, String currency) throws IOException {

       LOGGER.debug("amount {}, currency {}", amount, currency);


        Set<ExchangeDto> exchange = exchangeService.getExchange();
        LOGGER.debug("exchange {}", exchange);

        Optional<ExchangeDto> first =exchange
                .stream()
                .filter(currencyDto -> currencyDto.getCcy().equals(currency)).findFirst();
        AtomicReference<BigDecimal> multiply = new AtomicReference<>();
        first.ifPresentOrElse(
                currencyDto -> multiply.set(currencyDto.getSale().multiply(amount)), ()->{
                    throw new NotFoundCurrencyException("Currency "+currency+" not found in "+ Arrays.toString(exchange.toArray()));
                }
        );
        return multiply.get();
    }
}
