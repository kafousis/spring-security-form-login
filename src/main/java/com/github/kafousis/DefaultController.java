package com.github.kafousis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller @Slf4j
public class DefaultController {

    @GetMapping("/login")
    public String login() {

        if (SecurityUtils.isAuthenticated()){
           log.info("User is already authenticated.");
           return "/index";
        }
        return "/login";
    }

    @GetMapping("/")
    public String index() {
        return "/index";
    }

}
