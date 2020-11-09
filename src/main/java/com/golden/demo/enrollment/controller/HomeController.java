package com.golden.demo.enrollment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.golden.demo.enrollment.service.EnrollmentService;

import lombok.extern.slf4j.Slf4j;



@Controller
// works without /home
@RequestMapping("/home")
@Slf4j
public class HomeController {
    @Value("${spring.application.name}")
    String appName;
 
    @GetMapping("")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        log.info("homePage called");
        return "home";
    }
}