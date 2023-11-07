package org.example.service.imp;

import com.google.gson.Gson;
import org.example.dto.ExchangeDto;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExchangeServiceImpTest {

    private static ExchangeServiceImp exchangeService;

    @BeforeAll // всі @BeforeAll і AfterAll повинні бути статичними
    public static void inIt() {
        exchangeService = new ExchangeServiceImp();
    }

    @Test
    public void getExchangeTest() throws IOException {
        Set<ExchangeDto> exchange = exchangeService.getExchange(); // виклик exchangeService
        assertEquals(2, exchange.size()); // повернуться дві змінні - долар чи евро
    }

    @AfterAll // виконання наприкінці всіх тестів
    //  @AfterEach // виконання на прикінці кожного тесту
    public static void end() {
        System.out.println("The end");
    }

    private Method getResultForRequestMethod() throws NoSuchMethodException {
        // resultForGetRequest - метод з класу  ExchangeServiceImp + кажемо що приймає стрингу
        Method method = exchangeService.getClass().getDeclaredMethod("resultForGetRequest", String.class);
        method.setAccessible(true); // кажемо що цей метод треба зробити доступним ( він приваатний)
        return method; // і повертаємо цей метод
    }

    @Test
    void resultForRequestSuccess() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
              // отримуємо доступ до методу + рендомну айпішку
        String invoke = (String) getResultForRequestMethod()  // внизу завдяки size= виставляємо кількість обьектів
                .invoke(exchangeService, "https://random-data-api.com/api/v2/users?size=5&response_type=json");
        assertNotNull(invoke); // перевіряємо що це не нал
        Object[] objects = new Gson().fromJson(invoke, Object[].class);
        assertEquals(5, objects.length); // очікуємо повернення двух обьектів рендомних

    }

}
