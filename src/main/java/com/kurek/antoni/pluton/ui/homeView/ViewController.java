package com.kurek.antoni.pluton.ui.homeView;

import com.kurek.antoni.pluton.ui.UserInfo;
import com.kurek.antoni.pluton.ui.dtos.CreatePortfolioFormData;
import com.kurek.antoni.pluton.ui.dtos.PortfolioLink;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
public class ViewController {
    private final HomeHandler handler;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/portfolio/new")
    public String newPortfolioView(Model model) {
        model.addAttribute("formData", new CreatePortfolioFormData());
        return "new-portfolio";
    }

    @PostMapping("/portfolio/new")
    public String createPortfolio(CreatePortfolioFormData createPortfolioFormData) {
        String portfolioId = UUID.randomUUID().toString();
        log.info(createPortfolioFormData.toString());
        return "redirect:/portfolio/" + portfolioId;
    }


    @GetMapping("/portfolio/{id}")
    public String portfolioView(@PathVariable UUID id) {
        return "portfolio";
    }

    @ModelAttribute(name = "userInfo")
    public UserInfo userInfo(OAuth2AuthenticationToken token) {
        return new UserInfo(token);
    }

    @ModelAttribute(name = "ownedPortfolioLinks")
    public PortfolioLink[] ownedPortfolios(@ModelAttribute("userInfo") UserInfo userInfo) throws URISyntaxException,
            IOException, InterruptedException {
        return this.handler.getOwnedPortfolioLinks(userInfo.email());
    }
}
