package com.techelevator.tebucks.money.services;

import com.techelevator.tebucks.money.dao.TransferDao;
import com.techelevator.tebucks.money.model.AuthTokenDto;
import com.techelevator.tebucks.money.model.LoginDto;
import com.techelevator.tebucks.money.model.Transfer;
import com.techelevator.tebucks.money.model.TxLogDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Component
public class LoggerService {


    private static final RestTemplate restTemplate = new RestTemplate();
    private String authorizationToken;
    LoginDto loginDto = new LoginDto();

    public TxLogDto addLog(TxLogDto txLogDto) {
        logIn(loginDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authorizationToken);
        HttpEntity<TxLogDto> entity = new HttpEntity<>(txLogDto, headers);

        try {
            return restTemplate.postForObject("https://te-pgh-api.azurewebsites.net/api/TxLog", entity, TxLogDto.class);

        } catch (RestClientException e) {
        }
        return null;
    }

    public void logIn(LoginDto loginDto) {
        loginDto.setUsername("annalee54");
        loginDto.setPassword("annalee54");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authorizationToken);
        HttpEntity<LoginDto> entity = new HttpEntity<>(loginDto, headers);


            AuthTokenDto authTokenDto = restTemplate.postForObject("https://te-pgh-api.azurewebsites.net/api/Login", entity, AuthTokenDto.class);
            authorizationToken = authTokenDto.getToken();
        }
        }


