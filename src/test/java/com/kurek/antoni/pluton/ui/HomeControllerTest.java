package com.kurek.antoni.pluton.ui;

import com.kurek.antoni.pluton.ui.config.TestUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
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
        //when home page is viewed
        mockMvc.perform(get("/")
                                .with(oauth2Login()
                                              .oauth2User(user)))
               .andExpect(status().isOk())
               .andExpect(view().name("home"))
               .andExpect(content().string(containsString("Welcome " + userName)));
        //then portfolio-storage api should be called
        //todo sendAsync
        verify(client).send(argThat((HttpRequest r) -> r.uri().toString().contains("/ownedPortfolios/" + email)
                && r.method().equals("GET")), eq(HttpResponse.BodyHandlers.ofString()));
        //todo handle response
    }

}
