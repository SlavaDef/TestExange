package org.example.service.imp;

import org.example.dto.ExchangeDto;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExchangeServiceImpTest {

    private static  ExchangeServiceImp exchangeService;

    @BeforeAll
    public static void inIt(){
        exchangeService = new ExchangeServiceImp();
    }

    @Test
    public void getExchangeTest() throws IOException {
        Set<ExchangeDto> exchange = exchangeService.getExchange();
        assertEquals(2,exchange.size());
    }

    @AfterAll
    public static void end(){
        System.out.println("The end");
    }

}
