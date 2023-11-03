package org.example.service.imp;

import com.google.gson.Gson;
import org.example.dto.ExchangeDto;
import org.example.service.ExchangeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


import static org.example.utils.Constants.PRIVATBANK_EXCHANGE_URL;

public class ExchangeServiceImp implements ExchangeService {

    @Override
    public Set<ExchangeDto> getExchange() throws IOException {

        ExchangeDto[] exchangeDtos = new Gson()
                .fromJson(resultForGetRequest(PRIVATBANK_EXCHANGE_URL), ExchangeDto[].class);
        return Arrays.stream(exchangeDtos).collect(Collectors.toSet());


    }

    private String resultForGetRequest(String requestUrl) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(conn.getInputStream()))) {
           for(String line; (line = reader.readLine()) != null; ) {
               result.append(line);
           }
        }
        return result.toString();

    }
}
