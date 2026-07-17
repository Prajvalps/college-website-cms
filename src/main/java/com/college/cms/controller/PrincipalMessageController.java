package com.college.cms.controller;

import com.college.cms.model.PrincipalMessage;
import com.college.cms.service.PrincipalMessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/principal-message")
@CrossOrigin
public class PrincipalMessageController {

    private final PrincipalMessageService service;

    public PrincipalMessageController(PrincipalMessageService service) {
        this.service = service;
    }

    @PostMapping
    public PrincipalMessage save(@RequestBody PrincipalMessage message) {
        return service.save(message);
    }

    @GetMapping
    public PrincipalMessage get() {
        return service.get();
    }
}