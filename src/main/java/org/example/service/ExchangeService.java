package org.example.service;

import org.example.dto.ExchangeDto;

import java.io.IOException;
import java.util.Set;

public interface ExchangeService {

    // ціль методу витягнути з сайту приват курси валют
    Set<ExchangeDto> getExchange() throws IOException;

}
