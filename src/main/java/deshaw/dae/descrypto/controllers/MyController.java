package deshaw.dae.descrypto.controllers;

import deshaw.dae.descrypto.domain.User;

import deshaw.dae.descrypto.domain.Order;
import deshaw.dae.descrypto.services.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.util.List;

@RestController
@EnableScheduling
@RequestMapping("/api")
public class MyController {
    @Autowired
    private MyService service;



}
