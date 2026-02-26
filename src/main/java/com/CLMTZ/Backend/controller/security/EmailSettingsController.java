package com.CLMTZ.Backend.controller.security;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CLMTZ.Backend.dto.security.EmailSettingsDTO;
import com.CLMTZ.Backend.service.security.IEmailSettingsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/security/email-settings")
@RequiredArgsConstructor
public class EmailSettingsController {

    private final IEmailSettingsService service;

    @GetMapping
    public ResponseEntity<List<EmailSettingsDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailSettingsDTO> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmailSettingsDTO> save(@RequestBody EmailSettingsDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailSettingsDTO> update(@PathVariable("id") Integer id, @RequestBody EmailSettingsDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
