package com.kurek.antoni.pluton.ui.homeView;

import com.kurek.antoni.pluton.ui.UserInfo;
import com.kurek.antoni.pluton.ui.dtos.OwnedPortfolioDto;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@AllArgsConstructor
public class HomeController {
    private final HomeHandler handler;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @ModelAttribute(name = "userInfo")
    public UserInfo userInfo(OAuth2AuthenticationToken token) {
        return new UserInfo(token);
    }

    @ModelAttribute(name = "ownedPortfolios")
    public OwnedPortfolioDto[] ownedPortfolios(@ModelAttribute("userInfo") UserInfo userInfo) throws URISyntaxException, IOException
            , InterruptedException {
        return this.handler.getOwnedPortfolios(userInfo.email());
    }
}
