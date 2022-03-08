package com.kurek.antoni.pluton.ui;

import com.kurek.antoni.pluton.ui.config.FakeHttpResponse;
import com.kurek.antoni.pluton.ui.config.TestUser;
import com.kurek.antoni.pluton.ui.dtos.OwnedPortfolioDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

import static com.kurek.antoni.pluton.ui.config.Matchers.getOwnedPortfoliosRequest;
import static com.kurek.antoni.pluton.ui.config.Matchers.responseHandler;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {
    @MockBean
    HttpClient client;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldPullListOfOwnedPortfolios() throws Exception {
        //given authenticated user
        String userName = "John Doe";
        String email = "jdog@mail.co";
        TestUser user = new TestUser(email, userName);
        //given user owns 1 portfolio
        String portfolioName = "My stuff";
        HttpResponse<String> ownedPortfoliosResponse = FakeHttpResponse
                .builder()
                .statusCode(200)
                .body(List.of(new OwnedPortfolioDto(portfolioName, UUID.randomUUID())))
                .build();
        when(client.send(getOwnedPortfoliosRequest(email), responseHandler()))
                .thenReturn(ownedPortfoliosResponse);
        //when home page is viewed
        //then show list of owned portfolios
        mockMvc.perform(get("/")
                                .with(oauth2Login()
                                              .oauth2User(user)))
               .andExpect(status().isOk())
               .andExpect(view().name("home"))
               .andExpect(content().string(containsString("Welcome " + userName)))
               .andExpect(content().string(containsString(portfolioName)));
        //todo sendAsync
        verify(client).send(getOwnedPortfoliosRequest(email), responseHandler());
    }

}
