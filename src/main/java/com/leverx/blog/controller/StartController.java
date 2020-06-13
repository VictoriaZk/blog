package com.leverx.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller was made just for my personnel tranquility.
 * I am glad to see "Start page" after my project is runned.
 */
@Controller
@RequestMapping("/")
public class StartController {

    @GetMapping("/")
    public String hello() {
        return "start";
    }
}
