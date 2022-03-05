package com.kurek.antoni.pluton.ui;

import com.kurek.antoni.pluton.ui.config.TestUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldPullListOfOwnedPortfolios() throws Exception {
        //given authenticated user
        String userName = "Jan Sarna";
        TestUser user = new TestUser(userName);
        //when home page is viewed
        mockMvc.perform(get("/")
                                .with(oauth2Login()
                                              .oauth2User(user)))
               .andExpect(status().isOk())
               .andExpect(view().name("home"))
               .andExpect(content().string(containsString("Welcome " + userName)));
        //then portfolio-storage api should be called
        //todo make api call
    }

}
