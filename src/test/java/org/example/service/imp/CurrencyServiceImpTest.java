package org.example.service.imp;

import org.example.exceptions.NotFoundCurrencyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyServiceImpTest {

    private static CurrencyServiceImp currencyService;

    @BeforeAll
    public static void inIt() {
        currencyService = new CurrencyServiceImp();
    }

    @Test
    public void convertSuccessTest() throws IOException {
       BigDecimal usd =  currencyService.convert(BigDecimal.valueOf(10.0), "USD");
       // compareTo поверне 0 якщо числа рівні, чи -1, чи 1 якщо перше більше другого чи навпаки
        // тобто відбувається compare usd і нашого числа
        Assertions.assertEquals(0, usd.compareTo(BigDecimal.valueOf(375.000000)));
        //Assertions.assertEquals(BigDecimal.valueOf(375.000000),usd); // не вийде нулі відкинуться
    }

    @Test
    public void convertUnSuccessTest() throws IOException {
        // на випадок не вірне, вказання, назви валюти чи буде ексепшен
        // тобто при невірному форматі валюти буде наше виключення
        Assertions.assertThrowsExactly(NotFoundCurrencyException.class, () ->
        {currencyService.convert(BigDecimal.valueOf(10.0), "USD22");});


    }
}
