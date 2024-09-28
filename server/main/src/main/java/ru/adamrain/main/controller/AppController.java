package ru.adamrain.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/app")
public class AppController {

    @GetMapping("/all")
    public ResponseEntity<String> all(){
        System.out.println("all");
        log.info("all");
        log.error("all");
        log.warn("all");
        return ResponseEntity.ok("Ответ");
    }

    @GetMapping("/oll")
    public String oll(){
        System.out.println("oll");
        return "dfsdfsdfsd";
    }
}
