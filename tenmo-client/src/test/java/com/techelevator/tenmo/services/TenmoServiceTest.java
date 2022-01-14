package com.techelevator.tenmo.services;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

public class TenmoServiceTest {

    private static final String EXPECTED_API_URL = "http://localhost:8080/";

    @Mock
    private RestTemplate mockRestTemplate;

    @InjectMocks
    private TenmoService sut;



}