package pdevs.CursITU.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/index")
    public String testMessage() {
        return "Hello World!";
    }
}
