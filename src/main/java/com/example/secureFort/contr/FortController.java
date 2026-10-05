package com.example.secureFort.contr;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FortController {

    // PUBLIC
    @GetMapping("/marketplace")
    public String marketplace() {
        return "Public marketplace: food, clothing and supplies.";
    }

    // AUTHENTICATED USER
    @GetMapping("/entrance")
    public String entrance() {
        return "Welcome inside the fortress. You are authenticated.";
    }

    // ADMIN ONLY
    @GetMapping("/treasury")
    public String treasury() {
        return "Fortress treasury: restricted to administrators.";
    }
}
