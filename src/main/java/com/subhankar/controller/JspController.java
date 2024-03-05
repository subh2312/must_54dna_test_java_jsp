package com.subhankar.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class JspController {

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping({"/favicon.ico"})
    public void favicon() {
        // do nothing
    }

}
