package dev.spearkkk.untitle01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping(value = { "/", "/hello" })
    public String hello() {
        return "Hello, Health Checker !";
    }
}
