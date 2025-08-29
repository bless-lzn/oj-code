package com.limou.backendjudgeservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/judge")
public class JudgeController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
