package org.example;

import org.apache.log4j.BasicConfigurator;
import org.example.service.CurrencyService;
import org.example.service.imp.CurrencyServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;



public class App {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        BasicConfigurator.configure();

        CurrencyService currencyService = new CurrencyServiceImp();

        BigDecimal usd = null;

        try {
            usd = currencyService.convert(BigDecimal.valueOf(2), "USD");
        } catch (IOException e){
            LOGGER.error("Can't convert", e);
        }
        LOGGER.info("we will have grivens : {}", usd);




    }
}