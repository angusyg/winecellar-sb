package com.angusyg.winecellar.auth.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/signup")
    @RolesAllowed()
    public String signup() {
        return "Signed Up!!";
    }
}
