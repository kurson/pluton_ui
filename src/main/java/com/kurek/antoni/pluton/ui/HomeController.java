package com.kurek.antoni.pluton.ui;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@AllArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @ModelAttribute(name = "userInfo")
    public UserInfo userInfo(OAuth2AuthenticationToken token) {
        return new UserInfo(token);
    }
}
