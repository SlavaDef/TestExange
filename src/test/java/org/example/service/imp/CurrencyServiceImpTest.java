package org.example.service.imp;

import org.example.dto.ExchangeDto;
import org.example.exceptions.NotFoundCurrencyException;
import org.example.service.CurrencyService;
import org.example.service.ExchangeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

public class CurrencyServiceImpTest {

    private static CurrencyServiceImp currencyService;
    private static CurrencyServiceImp currencyServiceWithMock;
    private static ExchangeService exchangeServiceMock;

    private static CurrencyService currencyServiceMock;

    @BeforeAll
    public static void inIt() { // всі @BeforeAll і AfterAll повинні бути статичними
        currencyService = new CurrencyServiceImp();
        exchangeServiceMock = Mockito.mock(ExchangeService.class);
        currencyServiceMock = Mockito.mock(CurrencyService.class);
        currencyServiceWithMock = new CurrencyServiceImp(exchangeServiceMock);
    }

    @Test
     void convertSuccessTest() throws IOException { // тестові методи всі public void
       BigDecimal usd =  currencyService.convert(BigDecimal.valueOf(10.0), "USD");
       // compareTo поверне 0 якщо числа рівні, чи -1, чи 1 якщо перше більше другого чи навпаки
        // тобто відбувається compare usd і нашого числа
        Assertions.assertEquals(0, usd.compareTo(BigDecimal.valueOf(375.000000)));
        //Assertions.assertEquals(BigDecimal.valueOf(375.000000),usd); // не вийде нулі відкинуться
    }

    @Test
     void convertUnSuccessTest() throws IOException {
        // на випадок не вірне, вказання, назви валюти чи буде ексепшен
        // тобто при невірному форматі валюти буде наше виключення
        Assertions.assertThrowsExactly(NotFoundCurrencyException.class, () ->
        {
            currencyService.convert(BigDecimal.valueOf(10.0), "USD22");
        });

    }

        @Test
        void convertSuccessWithMock() throws IOException {
            Set<ExchangeDto> exchangeDtos = new LinkedHashSet<>();
            exchangeDtos.add(new ExchangeDto("USD","UAH",BigDecimal.valueOf(12),BigDecimal.valueOf(11)));
            exchangeDtos.add(new ExchangeDto("EUR","UAH",BigDecimal.valueOf(13),BigDecimal.valueOf(12)));
            Mockito.when(exchangeServiceMock.getExchange()).thenReturn(exchangeDtos);
            BigDecimal usd = currencyServiceWithMock.convert(BigDecimal.valueOf(10.0), "USD");
            BigDecimal eur = currencyServiceWithMock.convert(BigDecimal.valueOf(10.0), "EUR");
            Assertions.assertEquals(0,usd.compareTo(BigDecimal.valueOf(110.0)));
            Assertions.assertEquals(0,eur.compareTo(BigDecimal.valueOf(120.0)));
        }

        @Test
        void convertSuccessMock() throws IOException {
            Set<ExchangeDto> exchangeDtos = new LinkedHashSet<>(); // створили сет обьектів ExchangeDto

            // створили пару обьектів
            exchangeDtos.add(new ExchangeDto("USD","UAH",BigDecimal.valueOf(12),BigDecimal.valueOf(11)));
            exchangeDtos.add(new ExchangeDto("EUR","UAH",BigDecimal.valueOf(13),BigDecimal.valueOf(12)));

            Mockito.when(currencyServiceMock.convert(BigDecimal.valueOf(10.0), "USD")).thenReturn(BigDecimal.valueOf(123));
            Mockito.when(currencyServiceMock.convert(BigDecimal.valueOf(10.0), "EUR")).thenReturn(BigDecimal.valueOf(1234));
            BigDecimal usd = currencyServiceMock.convert(BigDecimal.valueOf(10.0), "USD");
            BigDecimal eur = currencyServiceMock.convert(BigDecimal.valueOf(10.0), "EUR");
            Assertions.assertEquals(0,usd.compareTo(BigDecimal.valueOf(123)));
            Assertions.assertEquals(0,eur.compareTo(BigDecimal.valueOf(1234)));
        }

        @Test
        void convertUnsuccessfulMock() throws IOException {
            Set<ExchangeDto> exchangeDtos = new LinkedHashSet<>();
            exchangeDtos.add(new ExchangeDto("USD","UAH",BigDecimal.valueOf(12),BigDecimal.valueOf(11)));
            exchangeDtos.add(new ExchangeDto("EUR","UAH",BigDecimal.valueOf(13),BigDecimal.valueOf(12)));
            Mockito.when(currencyServiceMock.convert(BigDecimal.valueOf(10.0), "USD")).thenThrow(NotFoundCurrencyException.class);
            BigDecimal bigDecimal = BigDecimal.valueOf(10.0);
            try {
                currencyServiceMock.convert(bigDecimal, "USD");
                Assertions.fail("Expected an NotFoundCurrencyException to be thrown");
            }catch (NotFoundCurrencyException | IOException e){
                Assertions.assertNull(e.getMessage());
            }
        }


    }

