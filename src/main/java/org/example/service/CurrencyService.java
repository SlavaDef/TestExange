package org.example.service;

import java.io.IOException;
import java.math.BigDecimal;

public interface CurrencyService {

    // сервіс отримує скільки в нас є валюти + якої саме - долари євро .... конвертує в гривні
    BigDecimal convert(BigDecimal amount, String currency) throws IOException;
}
