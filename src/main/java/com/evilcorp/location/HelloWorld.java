package com.evilcorp.location;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("/hello-world")
    public String getHelloWorld() {
        return "Hello, World!";
    }
}
