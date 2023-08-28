package com.backend.authservice.core.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/base")
public class BaseController {

    @GetMapping
    public ResponseEntity<?> permitAll() {
        return ResponseEntity.ok("Permit all");
    }

    @GetMapping("/authenticated")
    public ResponseEntity<?> authenticated() {
        return ResponseEntity.ok("Authenticated");
    }

    @GetMapping("/admin")
    public ResponseEntity<?> admin() {
        return ResponseEntity.ok("Admin");
    }
}
